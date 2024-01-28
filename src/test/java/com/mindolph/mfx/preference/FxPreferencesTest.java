package com.mindolph.mfx.preference;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mindolph.com@gmail.com
 */
class FxPreferencesTest {
    public static final String MFX_PREFERENCE_LIST_TEST = "com.mindolph.mfx.preference.list.test";
    private static final String DEFAULT_STRING = "default";
    private static final Integer DEFAULT_INT = 128;

    @Test
    void savePreference() {
        FxPreferences.getInstance().init(FxPreferencesTest.class);
        FxPreferences fxp = FxPreferences.getInstance();
        fxp.savePreference("key", "value");
        fxp.flush();
        Assertions.assertEquals("value", fxp.getPreference("key", String.class));
    }

    @Test
    void getPreference() {
        FxPreferences.getInstance().init(FxPreferencesTest.class);
        FxPreferences pp = FxPreferences.getInstance();
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

    @Test
    public void testList() {
        FxPreferences.getInstance().init(FxPreferencesTest.class);
        FxPreferences pp = FxPreferences.getInstance();
        List<String> list = Arrays.asList("a", "b");
        pp.savePreference(MFX_PREFERENCE_LIST_TEST, list);
        List<String> preferences = pp.getPreference(MFX_PREFERENCE_LIST_TEST, new ArrayList<>());
        for (String preference : preferences) {
            System.out.println(preference);
        }
        Assertions.assertEquals("a", preferences.get(0));
        Assertions.assertEquals("b", preferences.get(1));
    }

    private String randomKey() {
        return "k" + System.currentTimeMillis();
    }
}