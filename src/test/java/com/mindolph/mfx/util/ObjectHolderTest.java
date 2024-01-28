package com.mindolph.mfx.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mindolph.com@gmail.com
 */
class ObjectHolderTest {

    public static final String OBJECT_HOLDER = "object holder";

    @Test
    public void test() {
        ObjectHolder<String> objectHolder = new ObjectHolder<>(null);
        objectHolder.set(OBJECT_HOLDER);
        Assertions.assertEquals(OBJECT_HOLDER, objectHolder.get());
    }

}