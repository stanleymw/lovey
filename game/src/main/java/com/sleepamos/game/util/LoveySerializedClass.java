package com.sleepamos.game.util;

import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.annotations.LoveySerializableValue;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

public class LoveySerializedClass implements Serializable {
    private static final LoveySerializedClass ROOT = new LoveySerializedClass((byte) 0, null, new HashMap<>());

    private final byte version;

    private final LoveySerializedClass superclass;
    private final Map<String, Object> data;

    private LoveySerializedClass(byte version, LoveySerializedClass superclass, Map<String, Object> data) {
        this.version = version;
        this.superclass = superclass;
        this.data = data;
    }

    private LoveySerializedClass(byte version, Class<?> superClazz, Map<String, Object> data, LoveySerializable obj) {
        this(version, createIfLegalClass(superClazz, obj), data);
    }

    public LoveySerializedClass(LoveySerializable obj) {
        this(getClassVersion(obj.getClass()), obj.getClass().getSuperclass(), getObjectData(obj.getClass(), obj), obj);
    }

    private static byte getClassVersion(Class<?> clazz) {
        try {
            for(Field f : clazz.getDeclaredFields()) {
                if(f.getAnnotation(LoveySerializableClassVersion.class) != null) {
                    f.setAccessible(true);
                    return f.getByte(null);
                }
            }
        } catch (Exception e) {
            throw new NonFatalException("Reflection error while getting class version for " + clazz.getName(), e);
        }
        return 0;
    }

    private static Map<String, Object> getObjectData(Class<?> clazz, LoveySerializable obj) {
        Field[] classFields = clazz.getDeclaredFields();
        Map<String, Object> variableNameToValue = new HashMap<>(classFields.length);

        for(Field f : classFields) {
            if(!ReflectionUtil.isStatic(f) && ReflectionUtil.hasAnnotation(f, LoveySerializableValue.class)) {

            }
        }

        return variableNameToValue;
    }

    private static LoveySerializedClass createIfLegalClass(Class<?> clazz, LoveySerializable obj) {
        if(LoveySerializable.class.isAssignableFrom(clazz)) {
            return new LoveySerializedClass(getClassVersion(clazz), clazz.getSuperclass(), getObjectData(clazz, obj), obj);
        }

        return ROOT;
    }
}