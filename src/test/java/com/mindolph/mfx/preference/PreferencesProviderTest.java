package com.mindolph.mfx.preference;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

    private static final String DEFAULT_STRING = "default";
    private static final Integer DEFAULT_INT = 128;

    @Test
    public void testDefaultValue() {
        PreferencesProvider pp = PreferencesProvider.getInstance();
        Assertions.assertThrows(Exception.class, () -> pp.getPreference(randomKey(), String.class, null));
        Assertions.assertEquals(DEFAULT_STRING, pp.getPreference(randomKey(), String.class, DEFAULT_STRING));

        Assertions.assertThrows(Exception.class, () -> pp.getPreference(randomKey(), Integer.class, null));
        Assertions.assertEquals(DEFAULT_INT, pp.getPreference(randomKey(), Integer.class, DEFAULT_INT));

        Assertions.assertThrows(Exception.class, () -> pp.getPreference(randomKey(), Boolean.class, null));
        Assertions.assertEquals(Boolean.TRUE, pp.getPreference(randomKey(), Boolean.class, Boolean.TRUE));
        Assertions.assertEquals(Boolean.FALSE, pp.getPreference(randomKey(), Boolean.class, Boolean.FALSE));

        Assertions.assertThrows(Exception.class, () -> pp.getPreference(randomKey(), Font.class, null));
        Assertions.assertEquals(Font.getDefault(), pp.getPreference(randomKey(), Font.class, Font.getDefault()));

        Assertions.assertThrows(Exception.class, () -> pp.getPreference(randomKey(), Color.class, null));
        Assertions.assertEquals(Color.RED, pp.getPreference(randomKey(), Color.class, Color.RED));
    }

    private String randomKey() {
        return "k" + System.currentTimeMillis();
    }

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
