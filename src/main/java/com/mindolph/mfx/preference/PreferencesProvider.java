package com.mindolph.mfx.preference;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * @author allen
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
        else {
            prefs.put(key, String.valueOf(value));
        }
    }

    public <T> T getPreference(String key, Class<T> clazz) {
        return getPreference(key, clazz, null);
    }

    /**
     * Get preference by Class type with default value.
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getPreference(String key, Class<T> clazz, T defaultValue) {
        key = absoluteKey(key);
        Object val;
        if (clazz == Integer.class) {
            val = (T) Integer.valueOf(prefs.getInt(key, -1));
        }
        else if (clazz == Long.class) {
            val = (T) Long.valueOf(prefs.getLong(key, -1L));
        }
        else if (clazz == Boolean.class) {
            val = (T) Boolean.valueOf(prefs.getBoolean(key, Boolean.FALSE));
        }
        else if (clazz == Double.class) {
            val = (T) Double.valueOf(prefs.getDouble(key, -1f));
        }
        else if (clazz == byte[].class) {
            val = (T) prefs.getByteArray(key, (new byte[]{}));
        }
        else if (clazz == String.class) {
            val = (T) prefs.get(key, ""); // as string
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
            throw new RuntimeException("Default value can't be null");
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
        else if (def instanceof Double) {
            return prefs.getDouble(key, (Double) def);
        }
        else if (def instanceof byte[]) {
            return prefs.getByteArray(key, (byte[]) def);
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

    /**
     * Get the {@line java.util.prefs.Preferences} instance.
     *
     * @return
     */
    public Preferences getPrefs() {
        return prefs;
    }
}
