package com.sleepamos.game.serializer;

import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;
import com.sleepamos.game.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class LoveySerializationUtil {
    private LoveySerializationUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static byte getClassVersion(Class<?> clazz) {
        try {
            for (Field f : clazz.getDeclaredFields()) {
                if (ReflectionUtil.hasAnnotation(f, LoveySerializableClassVersion.class)) {
                    f.setAccessible(true);
                    return f.getByte(null);
                }
            }
        } catch (Exception e) {
            throw new NonFatalException("Reflection error while getting class version for " + clazz.getName(), e);
        }
        return 0;
    }

    public static String getSerializationName(Field f) {
        LoveySerializableValue annotation = f.getAnnotation(LoveySerializableValue.class);
        if (annotation != null) {
            return annotation.value();
        }
        return f.getName();
    }

    public static Map<String, String> serializedNameToClassName(Class<?> clazz) {
        Map<String, String> map = new HashMap<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (!ReflectionUtil.isStatic(f)) {
                map.put(getSerializationName(f), f.getName());
            }
        }
        return map;
    }
}
