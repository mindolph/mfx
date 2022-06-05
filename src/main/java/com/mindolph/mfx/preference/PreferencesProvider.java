package com.mindolph.mfx.preference;

import com.mindolph.mfx.util.ColorUtils;
import com.mindolph.mfx.util.FontUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author allen
 * @deprecated
 */
public class PreferencesProvider {

    public static Class<?> ownerClass;

    private static PreferencesProvider instance;

    private final Preferences prefs;

    /**
     * prefix adds to the preference key with dot '.',
     * like "prefix.my_pref"
     */
    private String prefix;

    private PreferencesProvider(Class clazz) {
        prefs = Preferences.userNodeForPackage(clazz);
    }

    /**
     * Class that identifies the application.
     *
     * @return
     */
    public static synchronized PreferencesProvider getInstance() {
        if (instance == null) {
            if (ownerClass == null) {
                ownerClass = PreferencesProvider.class;
            }
            System.out.println("Initialize preference for class: " + ownerClass);
            instance = new PreferencesProvider(ownerClass);
        }
        return instance;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private String absoluteKey(String key) {
        if (StringUtils.isNotBlank(prefix)) {
            return String.format("%s.%s", prefix, key);
        }
        return key;
    }

    /**
     * Save preference by the type of its value.
     *
     * @param key
     * @param value
     */
    public void savePreference(String key, Object value) {
        key = absoluteKey(key);
        if (value instanceof Integer) {
            prefs.putInt(key, (Integer) value);
        }
        else if (value instanceof Long) {
            prefs.putLong(key, (Long) value);
        }
        else if (value instanceof Boolean) {
            prefs.putBoolean(key, (Boolean) value);
        }
        else if (value instanceof Double) {
            prefs.putDouble(key, (Double) value);
        }
        else if (value instanceof byte[]) {
            prefs.putByteArray(key, (byte[]) value);
        }
        else if (value instanceof List) {
            prefs.put(key, StringUtils.join((List) value, ","));
        }
        else if (value instanceof Font) {
            prefs.put(key, font2str((Font) value));
        }
        else if (value instanceof Color) {
            prefs.putInt(key, ColorUtils.colorRgba((Color) value));
        }
        else {
            prefs.put(key, String.valueOf(value));
        }
    }

    /**
     * get preference by key and class type, return pre-defined default value if it doesn't exist.
     * the default value for integer or long will be -1, double or float will be -1.0, string will be ''.
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getPreference(String key, Class<T> clazz) {
        key = absoluteKey(key);
        Object val;
        if (clazz == Integer.class || clazz == int.class) {
            val = (T) Integer.valueOf(prefs.getInt(key, -1));
        }
        else if (clazz == Long.class || clazz == long.class) {
            val = (T) Long.valueOf(prefs.getLong(key, -1L));
        }
        else if (clazz == Boolean.class || clazz == boolean.class) {
            val = (T) Boolean.valueOf(prefs.getBoolean(key, Boolean.FALSE));
        }
        else if (clazz == Float.class || clazz == float.class) {
            val = (T) Float.valueOf(prefs.getFloat(key, -1f));
        }
        else if (clazz == Double.class || clazz == double.class) {
            val = (T) Double.valueOf(prefs.getDouble(key, -1f));
        }
        else if (clazz == byte[].class) {
            val = (T) prefs.getByteArray(key, (new byte[]{}));
        }
        else if (clazz == String.class) {
            val = (T) prefs.get(key, ""); // as string
        }
        else if (clazz == Font.class) {
            val = prefs.get(key, font2str(Font.getDefault()));
        }
        else if (clazz == Color.class) {
            int argb = prefs.getInt(key, 0);
            val = ColorUtils.colorFromRgba(argb);
        }
        else {
            throw new RuntimeException("Not supported class type: " + clazz);
        }
        if (val == null) {
            return null;
        }
        return (T) val;
    }

    /**
     * Get preference by key and filed of target object, return field's default value if it doesn't exist.
     *
     * @param key
     * @param field
     * @param target
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public <T> T getPreference(String key, Field field, Object target) throws IllegalAccessException {
        Class<T> clazz = (Class<T>) field.getType();
        field.setAccessible(true);
        Object val;
        if (clazz == Integer.class || clazz == int.class) {
            val = prefs.getInt(key, field.getInt(target));
        }
        else if (clazz == Long.class || clazz == long.class) {
            val = prefs.getLong(key, field.getLong(target));
        }
        else if (clazz == Boolean.class || clazz == boolean.class) {
            val = prefs.getBoolean(key, field.getBoolean(target));
        }
        else if (clazz == Float.class || clazz == float.class) {
            val = prefs.getFloat(key, field.getFloat(target));
        }
        else if (clazz == Double.class || clazz == double.class) {
            val = prefs.getDouble(key, field.getDouble(target));
        }
        else if (clazz == byte[].class) {
            val = prefs.getByteArray(key, (byte[]) field.get(target));
        }
        else if (clazz == String.class) {
            val = prefs.get(key, (String) field.get(target));
        }
        else if (clazz == Font.class) {
            val = (T) str2font(prefs.get(key, null), (Font) field.get(target));
        }
        else if (clazz == Color.class) {
            int argb = prefs.getInt(key, 0);
            val = argb == 0 ? field.get(target) : ColorUtils.colorFromRgba(argb);
        }
        else {
            throw new RuntimeException("Not supported class type: " + clazz);
        }
        if (val == null) {
            return null;
        }
        return (T) val;
    }

    /**
     * Get preference by key and class type, return provided default value if it doesn't exist.
     *
     * @param key
     * @param clazz
     * @param defaultValue can't be null
     * @return
     */
    public <T> T getPreference(String key, Class<T> clazz, T defaultValue) {
        key = absoluteKey(key);
        if (defaultValue == null) {
            throw new RuntimeException("default value for %s can't be null".formatted(key));
        }
        Object val;
        if (clazz == Integer.class || clazz == int.class) {
            val = prefs.getInt(key, (Integer) defaultValue);
        }
        else if (clazz == Long.class || clazz == long.class) {
            val = prefs.getLong(key, (Long) defaultValue);
        }
        else if (clazz == Boolean.class || clazz == boolean.class) {
            val = prefs.getBoolean(key, (Boolean) defaultValue);
        }
        else if (clazz == Float.class || clazz == float.class) {
            val = prefs.getFloat(key, (Float) defaultValue);
        }
        else if (clazz == Double.class || clazz == double.class) {
            val = prefs.getDouble(key, (Double) defaultValue);
        }
        else if (clazz == byte[].class) {
            val = prefs.getByteArray(key, ((byte[]) defaultValue));
        }
        else if (clazz == String.class) {
            val = prefs.get(key, (String) defaultValue);
        }
        else if (clazz == Font.class) {
            return (T) str2font(prefs.get(key, null), (Font) defaultValue);
        }
        else if (clazz == Color.class) {
            Integer v = prefs.getInt(key, 0);
            return v == 0 ? defaultValue : (T) ColorUtils.colorFromRgba(v);
        }
        else {
            throw new RuntimeException("Not supported class type: " + clazz);
        }
        if (val == null) {
            return defaultValue;
        }
        return (T) val;
    }

    /**
     * @param key
     * @param def default value if preference not exists.
     * @param <T> type of default value.
     * @return
     */
    public <T> Object getPreference(String key, T def) {
        key = absoluteKey(key);
        if (def == null) {
            throw new RuntimeException("default value for %s can't be null".formatted(key));
        }
        if (def instanceof Integer) {
            return prefs.getInt(key, (Integer) def);
        }
        else if (def instanceof Long) {
            return prefs.getLong(key, (Long) def);
        }
        else if (def instanceof Boolean) {
            return prefs.getBoolean(key, (Boolean) def);
        }
        else if (def instanceof Float) {
            return prefs.getFloat(key, (Float) def);
        }
        else if (def instanceof Double) {
            return prefs.getDouble(key, (Double) def);
        }
        else if (def instanceof byte[]) {
            return prefs.getByteArray(key, (byte[]) def);
        }
        else if (def instanceof Font) {
            return str2font(prefs.get(key, null), (Font) def);
        }
        else {
            return prefs.get(key, String.valueOf(def));
        }
    }

    /**
     * The preference must be separated by comma ','
     *
     * @param key
     * @param def
     * @return
     */
    public List<String> getPreferenceAndSplit(String key, List<String> def) {
        key = absoluteKey(key);
        String searchPaths = (String) this.getPreference(key, "");
        if (StringUtils.isNotBlank(searchPaths)) {
            String[] split = StringUtils.split(searchPaths, ",");
            if (split != null && split.length > 0) {
                List<String> ret = new ArrayList<>();
                Collections.addAll(ret, split);
                return ret;
            }
        }
        return def;
    }

    public void removePreference(String key) {
        prefs.remove(key);
    }

    public void flush() {
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the {@line java.util.prefs.Preferences} instance.
     *
     * @return
     */
    public Preferences getPrefs() {
        return prefs;
    }

    public static String font2str(Font font) {
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
    public static Font str2font(String text, Font defaultFont) {
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
