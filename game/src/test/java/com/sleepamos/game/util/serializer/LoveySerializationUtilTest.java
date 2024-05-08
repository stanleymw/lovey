package com.sleepamos.game.util.serializer;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializationUtilTest {
    @Test
    void testGetClassVersion() {
        assertEquals(LoveySerializationUtil.getClassVersion(SerializationClassTest.class), 10);
    }

    @Test
    void testGetSerializationName() throws NoSuchFieldException {
        Field f = SerializationClassTest.class.getDeclaredField("i");
        assertEquals(LoveySerializationUtil.getSerializationName(f), "not i");
        Field g = SerializationClassTest.class.getDeclaredField("iA");
        assertEquals(LoveySerializationUtil.getSerializationName(g), "iA");
    }

    @Test
    void testGetSerializationMap() {
        System.out.println(LoveySerializationUtil.serializedNameToClassName(SerializationClassTest.class));
    }
}
