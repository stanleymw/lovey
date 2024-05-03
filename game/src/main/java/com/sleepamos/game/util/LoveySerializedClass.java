package com.sleepamos.game.util;

import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.annotations.LoveySerializableValue;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LoveySerializedClass implements Serializable {
    private static final byte ROOT_VERSION = 0;

    private final boolean isRootClass;

    private final byte version;

    private final LoveySerializedClass superclass;

    private final Class<?> storedClazz;
    private final Map<String, Object> data;

    private LoveySerializedClass(byte version, LoveySerializedClass superclass, Class<?> storedClazz, Map<String, Object> data, boolean isRootClass) {
        this.version = version;
        this.superclass = superclass;
        this.storedClazz = storedClazz;
        this.data = data;
        this.isRootClass = isRootClass;
    }

    private LoveySerializedClass(byte version, LoveySerializedClass superclass, Class<?> storedClazz, Map<String, Object> data) {
        this(version, superclass, storedClazz,  data, false);
    }

    private LoveySerializedClass(byte version, Class<?> superClazz, Class<?> storedClazz, Map<String, Object> data, LoveySerializable obj) {
        this(version, createIfLegalClass(superClazz, obj), storedClazz, data);
    }

    public LoveySerializedClass(LoveySerializable obj) {
        this(getClassVersion(obj.getClass()), obj.getClass().getSuperclass(), obj.getClass(), getObjectData(obj.getClass(), obj), obj);
    }

    public byte getVersion() {
        return version;
    }

    public LoveySerializedClass getSuperclass() {
        return superclass;
    }

    public Class<?> getStoredClazz() {
        return storedClazz;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public boolean isRootClass() {
        return this.isRootClass;
    }

    private static byte getClassVersion(Class<?> clazz) {
        try {
            for(Field f : clazz.getDeclaredFields()) {
                if(ReflectionUtil.hasAnnotation(f, LoveySerializableClassVersion.class)) {
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

        try {
            for (Field f : classFields) {
                f.setAccessible(true);
                if (!ReflectionUtil.isStatic(f)) {
                    LoveySerializableValue annotation = f.getAnnotation(LoveySerializableValue.class);
                    if (annotation != null) {
                        variableNameToValue.put(annotation.value(), f.get(obj));
                    }
                }
            }
        } catch(Exception e) {
            throw new NonFatalException("Failed to get object data from class def " + clazz.getName() + " during serialization", e);
        }

        return variableNameToValue;
    }

    private static LoveySerializedClass generateStoredRootObject(Class<?> rootClazz) {
        return new LoveySerializedClass(ROOT_VERSION, null, rootClazz, new HashMap<>());
    }

    private static LoveySerializedClass createIfLegalClass(Class<?> clazz, LoveySerializable obj) {
        if(LoveySerializable.class.isAssignableFrom(clazz)) {
            return new LoveySerializedClass(getClassVersion(clazz), clazz.getSuperclass(), clazz, getObjectData(clazz, obj), obj);
        }

        return generateStoredRootObject(clazz.getSuperclass());
    }
}