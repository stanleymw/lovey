package com.sleepamos.game.serializer;

import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.util.ReflectionUtil;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoveySerializedClass implements Serializable {
    private static final byte ROOT_VERSION = 0;
    @Serial
    private static final long serialVersionUID = 5653976851902974527L;

    private final boolean isRootClass;

    private final byte version;

    private final LoveySerializedClass superclass;

    private final Class<?> storedClazz;
    private final List<LoveySerializedClassDataEntry> data;

    private LoveySerializedClass(byte version, LoveySerializedClass superclass, Class<?> storedClazz, List<LoveySerializedClassDataEntry> data, boolean isRootClass) {
        this.version = version;
        this.superclass = superclass;
        this.storedClazz = storedClazz;
        this.data = data;
        this.isRootClass = isRootClass;
    }

    private LoveySerializedClass(byte version, LoveySerializedClass superclass, Class<?> storedClazz, List<LoveySerializedClassDataEntry> data) {
        this(version, superclass, storedClazz, data, false);
    }

    private LoveySerializedClass(byte version, Class<?> superClazz, Class<?> storedClazz, List<LoveySerializedClassDataEntry> data, LoveySerializable obj) {
        this(version, createIfLegalClass(superClazz, obj), storedClazz, data);
    }

    public LoveySerializedClass(LoveySerializable obj) {
        this(LoveySerializationUtil.getClassVersion(obj.getClass()), obj.getClass().getSuperclass(), obj.getClass(), getObjectData(obj.getClass(), obj), obj);
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

    public List<LoveySerializedClassDataEntry> getData() {
        return data;
    }

    public boolean isRootClass() {
        return this.isRootClass;
    }

    private static List<LoveySerializedClassDataEntry> getObjectData(Class<?> clazz, LoveySerializable obj) {
        Field[] classFields = clazz.getDeclaredFields();
        List<LoveySerializedClassDataEntry> variableNameToValue = new ArrayList<>(classFields.length);

        try {
            for (Field f : classFields) {
                f.setAccessible(true);
                if (!ReflectionUtil.isStatic(f)) {
                    if(LoveySerializable.class.isAssignableFrom(f.getType())) { // if the sub-variable is LoveySerializable, we want to serialize it as such.
                        variableNameToValue.add(new LoveySerializedClassDataEntry(f.getName(), new LoveySerializedClass((LoveySerializable) f.get(obj))));
                    } else {
                        variableNameToValue.add(new LoveySerializedClassDataEntry(LoveySerializationUtil.getSerializationName(f), f.get(obj)));
                    }
                }
            }
        } catch(Exception e) {
            throw new NonFatalException("Failed to get object data from class def " + clazz.getName() + " during serialization", e);
        }

        return variableNameToValue;
    }

    private static LoveySerializedClass generateStoredRootObject(Class<?> rootClazz) {
        return new LoveySerializedClass(ROOT_VERSION, null, rootClazz, new ArrayList<>(), true);
    }

    private static LoveySerializedClass createIfLegalClass(Class<?> clazz, LoveySerializable obj) {
        if(LoveySerializable.class.isAssignableFrom(clazz)) {
            return new LoveySerializedClass(LoveySerializationUtil.getClassVersion(clazz), clazz.getSuperclass(), clazz, getObjectData(clazz, obj), obj);
        }
        return generateStoredRootObject(clazz);
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof LoveySerializedClass other &&
                this.version == other.version &&
                this.data.equals(other.data) &&
                this.isRootClass == other.isRootClass &&
                this.storedClazz == other.storedClazz && // note: Class.equals() is just ==
                (this.superclass == other.superclass || this.superclass.equals(other.superclass))); // == implies null
    }
}