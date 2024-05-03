package com.sleepamos.game.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ReflectionUtil {
    public static boolean isStatic(Field f) {
        return Modifier.isStatic(f.getModifiers());
    }

    public static boolean hasAnnotation(Field f, Class<? extends Annotation> annotationClazz) {
        return f.getAnnotation(annotationClazz) != null;
    }
}
