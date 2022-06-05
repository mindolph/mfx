package com.mindolph.mfx.preference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author allen
 */
class FxPreferencesTest {

    @Test
    void savePreference() {
        FxPreferences.getIns().init(FxPreferencesTest.class);
        FxPreferences fxp = FxPreferences.getIns();
        fxp.savePreference("key", "value");
        fxp.flush();
        Assertions.assertEquals("value", fxp.getPreference("key", String.class));
    }

    @Test
    void getPreference() {

    }
}