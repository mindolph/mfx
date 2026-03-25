package com.mindolph.mfx.i18n;

import com.mindolph.mfx.preference.FxPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalization helper class for managing application locales and resource bundles.
 * Supports English and Simplified Chinese.
 * The default preference key is "general.language", if you want to change it, assign the static I18nHelper.LANG_PREF_KEY direcctly.
 * <p>
 * Supports loading multiple resource bundles. Use {@link #addBundle(String)} to register additional bundles.
 *
 * @author Mindolph Team
 * @since 3.0.1
 */
public class I18nHelper {

    public static String LANG_PREF_KEY = "general.language";

    private static final Logger log = LoggerFactory.getLogger(I18nHelper.class);

    private static final String DEFAULT_BUNDLE_NAME = "messages";

    private static I18nHelper instance;

    private List<ResourceBundle> resourceBundles;
    private List<String> bundleNames;
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
        resourceBundles = new ArrayList<>();
        bundleNames = new ArrayList<>();
        bundleNames.add(DEFAULT_BUNDLE_NAME);

        String languageCode = FxPreferences.getInstance().getPreference(LANG_PREF_KEY, "");

        if (languageCode == null || languageCode.isEmpty()) {
            // Use system default locale if no preference is set
            currentLocale = Locale.getDefault();
        }
        else if ("zh_CN".equals(languageCode)) {
            currentLocale = Locale.SIMPLIFIED_CHINESE;
        }
        else if ("en".equals(languageCode)) {
            currentLocale = Locale.ENGLISH;
        }
        else {
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
        resourceBundles.clear();
        for (String bundleName : bundleNames) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(bundleName, currentLocale);
                resourceBundles.add(bundle);
                log.info("Resource bundle loaded: {} for locale: {}", bundleName, currentLocale);
            } catch (Exception e) {
                log.warn("Failed to load resource bundle: {} for locale: {}", bundleName, currentLocale, e);
            }
        }
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
        }
        else if ("en".equals(languageCode)) {
            locale = Locale.ENGLISH;
        }
        else {
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
     * Searches through all registered resource bundles in order.
     *
     * @param key the key for the localized string
     * @return the localized string, or the key if not found
     */
    public String get(String key) {
        for (ResourceBundle bundle : resourceBundles) {
            try {
                return bundle.getString(key);
            } catch (Exception e) {
                // Continue to next bundle
            }
        }
        log.warn("Key '{}' not found in any resource bundle for locale {}", key, currentLocale);
        return key;
    }

    /**
     * Get a localized string by key with parameter.
     *
     * @param key
     * @param p1
     * @return
     */
    public String get(String key, String p1) {
        for (ResourceBundle bundle : resourceBundles) {
            try {
                String v = bundle.getString(key);
                return v.formatted(p1);
            } catch (Exception e) {
                // Continue to next bundle
            }
        }
        return key;
    }

    /**
     * Get a localized string by key with parameters.
     * @param key
     * @param p1
     * @param p2
     * @return
     */
    public String get(String key, String p1, String p2) {
        for (ResourceBundle bundle : resourceBundles) {
            try {
                String v = bundle.getString(key);
                return v.formatted(p1, p2);
            } catch (Exception e) {
                // Continue to next bundle
            }
        }
        return key;
    }


    /**
     * Get the primary resource bundle (default messages bundle).
     * For multiple bundles, consider using {@link #getAllBundles()}.
     *
     * @return the primary resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundles.isEmpty() ? null : resourceBundles.get(0);
    }

    /**
     * Get all registered resource bundles.
     *
     * @return list of all resource bundles
     */
    public List<ResourceBundle> getAllBundles() {
        return resourceBundles;
    }

    /**
     * Add a custom resource bundle to be loaded.
     * The bundle will be loaded with the current locale.
     * Call this method before initializing UI components.
     *
     * @param bundleName the base name of the resource bundle (e.g., "custom_messages")
     * @return true if the bundle was added successfully
     */
    public boolean addBundle(String bundleName) {
        if (bundleName == null || bundleName.isEmpty()) {
            log.warn("Bundle name cannot be null or empty");
            return false;
        }

        if (bundleNames.contains(bundleName)) {
            log.warn("Bundle '{}' is already registered", bundleName);
            return false;
        }

        bundleNames.add(bundleName);
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, currentLocale);
            resourceBundles.add(bundle);
            log.info("Custom resource bundle added: {} for locale: {}", bundleName, currentLocale);
            return true;
        } catch (Exception e) {
            log.warn("Failed to load custom resource bundle: {} for locale: {}", bundleName, currentLocale, e);
            return false;
        }
    }

    /**
     * Remove a registered resource bundle.
     *
     * @param bundleName the base name of the resource bundle to remove
     * @return true if the bundle was removed successfully
     */
    public boolean removeBundle(String bundleName) {
        if (bundleName == null || bundleName.isEmpty()) {
            log.warn("Bundle name cannot be null or empty");
            return false;
        }

        if (DEFAULT_BUNDLE_NAME.equals(bundleName)) {
            log.warn("Cannot remove the default bundle: {}", DEFAULT_BUNDLE_NAME);
            return false;
        }

        int index = bundleNames.indexOf(bundleName);
        if (index >= 0) {
            bundleNames.remove(index);
            if (index < resourceBundles.size()) {
                resourceBundles.remove(index);
            }
            log.info("Resource bundle removed: {}", bundleName);
            return true;
        }
        log.warn("Bundle '{}' not found", bundleName);
        return false;
    }

    /**
     * Reload all registered resource bundles with the current locale.
     * This is useful when you want to refresh the bundles after adding or removing custom bundles.
     */
    public void reloadAllBundles() {
        reloadResourceBundle();
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
