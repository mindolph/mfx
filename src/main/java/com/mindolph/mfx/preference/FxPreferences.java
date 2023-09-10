package com.mindolph.mfx.preference;

import com.mindolph.mfx.util.ColorUtils;
import com.mindolph.mfx.util.FontUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;
import org.swiftboot.util.pref.Converter;
import org.swiftboot.util.pref.PreferenceManager;
import org.swiftboot.util.pref.StringListConverter;
import org.swiftboot.util.pref.StringSetConverter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Call init() before using.
 *
 * @author allen
 */
public class FxPreferences {

    private static FxPreferences ins;
    private PreferenceManager pm;

    private FxPreferences() {

    }

    public static synchronized FxPreferences getInstance() {
        if (ins == null) {
            ins = new FxPreferences();
        }
        return ins;
    }

    public void init(Class<?> ownerClass) {
        if (pm == null) {
            pm = PreferenceManager.getInstance(ownerClass);
            pm.setConverter(List.class, new StringListConverter());
            pm.setConverter(Set.class, new StringSetConverter());
            pm.setConverter(Font.class, new Converter<Font, String>() {
                @Override
                public Font deserialize(String prefString) {
                    return str2font(prefString, null);
                }

                @Override
                public String serialize(Font valueObject) {
                    return font2str(valueObject);
                }

                @Override
                public Class<String> getSaveAs() {
                    return String.class;
                }
            });
            pm.setConverter(Color.class, new Converter<Color, Integer>() {
                @Override
                public Color deserialize(Integer prefValue) {
                    return ColorUtils.colorFromRgba(prefValue);
                }

                @Override
                public Integer serialize(Color value) {
                    return ColorUtils.colorRgba(value);
                }

                @Override
                public Class<Integer> getSaveAs() {
                    return Integer.class;
                }
            });
        }
    }

    public void addConverter(Class<?> clazz, Converter converter) {
        pm.setConverter(clazz, converter);
    }

    public void removeConverter(Class<?> clazz) {
        pm.removeConverter(clazz);
    }

    public void savePreference(String key, Object value) {
        pm.savePreference(key, value);
    }

    public void savePreferenceFlush(String key, Object value, boolean flush) {
        pm.savePreference(key, value);
        if (flush) pm.flush();
    }

    public <T> T getPreference(String key, Class<T> clazz, T defaultValue) {
        return pm.getPreference(key, clazz, defaultValue);
    }

    public <T> T getPreference(String key, Field field, Object target) throws IllegalAccessException {
        return pm.getPreference(key, field, target);
    }

    public <T> T getPreference(String key, Class<T> clazz) {
        return pm.getPreference(key, clazz);
    }

    public <T> T getPreference(String key, T def) {
        return pm.getPreference(key, def);
    }

    public void removePreference(String key) {
        pm.removePreference(key);
    }

    /**
     * Whether a preference exists.
     *
     * @param key
     * @return
     */
    public boolean isPreferenceExist(String key) {
        return pm.getPreference(key) != null;
    }

    public void flush() {
        pm.flush();
    }


    private static String font2str(Font font) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(font.getFamily()).append('|').append(font.getStyle()).append('|').append(font.getSize());
        return buffer.toString();
    }

    /**
     * return default font if text is blank or informal.
     *
     * @param text
     * @param defaultFont
     * @return
     */
    private static Font str2font(String text, Font defaultFont) {
        if (StringUtils.isBlank(text)) {
            return defaultFont;
        }
        String[] fields = StringUtils.split(text, "|");
        if (fields.length != 3) {
            return defaultFont;
        }
        try {
            return Font.font(fields[0], FontUtils.fontWeight(fields[1]), FontUtils.fontPosture(fields[1]), Double.parseDouble(fields[2]));
        } catch (NumberFormatException ex) {
            return defaultFont;
        }
    }
}
