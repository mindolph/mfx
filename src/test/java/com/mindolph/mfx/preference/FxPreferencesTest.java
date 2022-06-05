package com.mindolph.mfx.preference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author allen
 */
class FxPreferencesTest {

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

    }
}