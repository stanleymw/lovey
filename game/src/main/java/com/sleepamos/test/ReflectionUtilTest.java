package com.sleepamos.test;

import com.sleepamos.game.util.ReflectionUtil;
import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.annotations.LoveySerializableValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilTest {
    @LoveySerializableClassVersion
    public static final int STATIC_VAR = 2;

    @LoveySerializableValue("notStaticVar")
    public final int notStaticAnnotatedVar = 3;

    public final int notAnnotatedVar = 4;

    @Test
    void isStatic() throws NoSuchFieldException {
        assertTrue(ReflectionUtil.isStatic(ReflectionUtilTest.class.getDeclaredField("STATIC_VAR")));

        assertFalse(ReflectionUtil.isStatic(ReflectionUtilTest.class.getDeclaredField("notStaticAnnotatedVar")));
    }

    @Test
    void hasAnnotation() throws NoSuchFieldException {
        assertTrue(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("STATIC_VAR"), LoveySerializableClassVersion.class));
        assertFalse(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("STATIC_VAR"), LoveySerializableValue.class));

        assertTrue(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("notStaticAnnotatedVar"), LoveySerializableValue.class));
        assertFalse(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("notStaticAnnotatedVar"), LoveySerializableClassVersion.class));

        assertFalse(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("notAnnotatedVar"), LoveySerializableValue.class));
        assertFalse(ReflectionUtil.hasAnnotation(ReflectionUtilTest.class.getDeclaredField("notAnnotatedVar"), LoveySerializableClassVersion.class));
    }
}