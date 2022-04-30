package com.mindolph.mfx.preference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author allen
 */
public class PreferencesProviderTest {

    public static final String MFX_PREFERENCE_LIST_TEST = "com.mindolph.mfx.preference.list.test";

    @Test
    public void testDataType() {
        PreferencesProvider pp = PreferencesProvider.getInstance();
        pp.savePreference("test", Boolean.FALSE);
        Boolean test = pp.<Boolean>getPreference("test", Boolean.class);
        System.out.println(test);

        pp.savePreference("unknown-object", new PreferencesProviderTest());
        Assertions.assertThrows(RuntimeException.class, () -> pp.getPreference("unknown-object", PreferencesProviderTest.class));
    }

    @Test
    public void testList() {
        PreferencesProvider pp = PreferencesProvider.getInstance();
        List<String> list = Arrays.asList("a", "b");
        pp.savePreference(MFX_PREFERENCE_LIST_TEST, list);
        List<String> preferences = pp.getPreferenceAndSplit(MFX_PREFERENCE_LIST_TEST, new ArrayList<>());
        for (String preference : preferences) {
            System.out.println(preference);
        }
    }
}
