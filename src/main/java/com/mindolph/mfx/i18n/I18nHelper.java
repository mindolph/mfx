package com.mindolph.mfx.i18n;

import com.mindolph.mfx.preference.FxPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalization helper class for managing application locales and resource bundles.
 * Supports English and Simplified Chinese.
 * The default preference key is "general.language", if you want to change it, assign the static I18nHelper.LANG_PREF_KEY direcctly.
 *
 * @author Mindolph Team
 * @since 3.0.1
 */
public class I18nHelper {

    public static String LANG_PREF_KEY = "general.language";

    private static final Logger log = LoggerFactory.getLogger(I18nHelper.class);

    private static final String BUNDLE_NAME = "messages";

    private static I18nHelper instance;

    private ResourceBundle resourceBundle;
    private Locale currentLocale;

    private I18nHelper() {
        init();
    }

    /**
     * Get the singleton instance of I18nHelper.
     *
     * @return the singleton instance
     */
    public static I18nHelper getInstance() {
        if (instance == null) {
            instance = new I18nHelper();
        }
        return instance;
    }

    /**
     * Initialize the resource bundle based on user preference or system locale.
     */
    private void init() {
        String languageCode = FxPreferences.getInstance().getPreference(LANG_PREF_KEY, "");
        
        if (languageCode == null || languageCode.isEmpty()) {
            // Use system default locale if no preference is set
            currentLocale = Locale.getDefault();
        } else if ("zh_CN".equals(languageCode)) {
            currentLocale = Locale.SIMPLIFIED_CHINESE;
        } else if ("en".equals(languageCode)) {
            currentLocale = Locale.ENGLISH;
        } else {
            currentLocale = Locale.getDefault();
        }

        // Ensure we only support en and zh_CN
        if (!Locale.ENGLISH.equals(currentLocale) && !Locale.SIMPLIFIED_CHINESE.equals(currentLocale)) {
            log.warn("Locale {} is not fully supported, falling back to English", currentLocale);
            currentLocale = Locale.ENGLISH;
        }

        reloadResourceBundle();
    }

    /**
     * Reload the resource bundle with the current locale.
     */
    private void reloadResourceBundle() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
        log.info("Resource bundle loaded for locale: {}", currentLocale);
    }

    /**
     * Set the application locale and reload the resource bundle.
     * Note: UI components need to be updated manually after calling this method.
     *
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            log.warn("Cannot set null locale");
            return;
        }
        
        // Only support English and Simplified Chinese
        if (!Locale.ENGLISH.equals(locale) && !Locale.SIMPLIFIED_CHINESE.equals(locale)) {
            log.warn("Locale {} is not supported, use English or Simplified Chinese", locale);
            return;
        }

        this.currentLocale = locale;
        
        // Save preference
        String languageCode = Locale.SIMPLIFIED_CHINESE.equals(locale) ? "zh_CN" : "en";
        FxPreferences.getInstance().savePreference(LANG_PREF_KEY, languageCode);
        
        reloadResourceBundle();
    }

    /**
     * Set the application locale by language code string.
     *
     * @param languageCode the language code (e.g., "en", "zh_CN")
     */
    public void setLocale(String languageCode) {
        if (languageCode == null || languageCode.isEmpty()) {
            log.warn("Cannot set null or empty language code");
            return;
        }

        Locale locale;
        if ("zh_CN".equals(languageCode)) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else if ("en".equals(languageCode)) {
            locale = Locale.ENGLISH;
        } else {
            log.warn("Unsupported language code: {}", languageCode);
            return;
        }

        setLocale(locale);
    }

    /**
     * Get the current locale.
     *
     * @return the current locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Get a localized string by key.
     *
     * @param key the key for the localized string
     * @return the localized string, or the key if not found
     */
    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            log.warn("Key '{}' not found in resource bundle for locale {}", key, currentLocale);
            return key;
        }
    }

    /**
     * Get a localized string by key with default value.
     *
     * @param key the key for the localized string
     * @param defaultValue the default value if key is not found
     * @return the localized string, or the default value if not found
     */
    public String get(String key, String defaultValue) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get the resource bundle.
     *
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Check if the current locale is Simplified Chinese.
     *
     * @return true if the current locale is Simplified Chinese
     */
    public boolean isChinese() {
        return Locale.SIMPLIFIED_CHINESE.equals(currentLocale);
    }

    /**
     * Check if the current locale is English.
     *
     * @return true if the current locale is English
     */
    public boolean isEnglish() {
        return Locale.ENGLISH.equals(currentLocale);
    }
}
