package com.sleepamos.test;

import com.sleepamos.game.util.LoveySerializationUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializationUtilTest {
    @Test
    void testGetClassVersion() {
        assertEquals(LoveySerializationUtil.getClassVersion(LoveyTestClass.class), 10);
    }

    @Test
    void testGetSerializationName() throws NoSuchFieldException {
        Field f = LoveyTestClass.class.getDeclaredField("i");
        assertEquals(LoveySerializationUtil.getSerializationName(f), "not i");
        Field g = LoveyTestClass.class.getDeclaredField("iA");
        assertEquals(LoveySerializationUtil.getSerializationName(g), "iA");
    }

    @Test
    void testGetSerializationMap() {
        System.out.println(LoveySerializationUtil.serializedNameToClassName(LoveyTestClass.class));
    }
}
