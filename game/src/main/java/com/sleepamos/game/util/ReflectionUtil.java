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

    public static boolean typeIsListOrArray(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz) || clazz.isArray();
    }

    public static Class<?> getTypeOfListOrArray(Class<?> listOrArrayClazz) {
        if(!typeIsListOrArray(listOrArrayClazz)) {
            throw new NonFatalException("Type " + listOrArrayClazz.getName() + " is not a list or array!");
        }
        if(List.class.isAssignableFrom(listOrArrayClazz)) {
            return (Class<?>) ((ParameterizedType) listOrArrayClazz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return listOrArrayClazz.getComponentType();
    }

    public static boolean typeNameIsPrimitive(String typeName) {
        return typeName.equals("boolean") ||
                typeName.equals("character") ||
                typeName.equals("byte") ||
                typeName.equals("short") ||
                typeName.equals("int") ||
                typeName.equals("long") ||
                typeName.equals("float") ||
                typeName.equals("double");
    }
}
