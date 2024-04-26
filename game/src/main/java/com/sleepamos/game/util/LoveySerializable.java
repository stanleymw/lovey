package com.sleepamos.game.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom serialization interface that is able to handle file versioning smoothly.
 * 
 * Ex:
 * Given a class
 * {@snippet lang = "java":
 * public class A implements LoveySerializable {
 *     public static final byte VERSION = 10;
 * 
 *     private double d;
 *     private boolean bool;
 *     private B b; // B implements LoveySerializable
 *     private transient C c; // C does not implement LoveySerializable
 *     private transient B anotherB; // B implements LoveySerializable
 *     private C c; // this would issue a warning on serialize if uncommented since C does not implement LoveySerializable. C would not be included in the final serialization.
 * 
 *     // Class continues...
 * }
 * }
 * The format of the serialized file would be the following without line breaks or information in parentheses:
 * type A: (A must be of the root type being serialized, any following class structure definitions after A are for included subelements)
 * version VERSION (the value, stored as a byte) (note: this value will NOT be read from the class; instead it must be passed in to the serializer)
 * double;
 * boolean;
 * val B;
 * fin;
 * type B:
 * version VERSION (the value)
 * ... and the variables of B, same format as A.
 * fin;
 * 
 */
public interface LoveySerializable {
    default LoveySerializedComponent toSerializedComponent() {
        return new LoveySerializedComponent();
    }

    static String serialize(LoveySerializable s) {
        return serialize(s, (byte) 0);
    }

    static String serialize(LoveySerializable s, byte version) {
        String serialized = String.valueOf(version);

        return serialized;
    }

    static LoveySerializable deserialize(String s) {
        return deserialize(s, (byte) 0);
    }

    static LoveySerializable deserialize(String s, byte expectedVersion) {
        return deserialize(s, expectedVersion, (serialized, fileVersion, eVersion) -> {
            throw new NonFatalException();
        });
    }

    static LoveySerializable deserialize(String s, byte expectedVersion, VersionMismatchedDeserializer onVersionMismatch) {
        byte version = s.getBytes()[0];
        if(version != expectedVersion) {
            return onVersionMismatch.deserialize(s, version, expectedVersion);
        }

        return null;
    }

    /**
     * Handles the conversion from a LoveySerializable serialized in a different (prior) version to the latest version.
     */
    @FunctionalInterface
    interface VersionMismatchedDeserializer {
        LoveySerializable deserialize(String serialized, byte fileVersion, byte expectedVersion);
    }

    record LoveySerializedComponent(String varName, List<Byte> bytes) {
        public LoveySerializedComponent() {
            this("", new ArrayList<>());
        }

        public LoveySerializedComponent(String varName) {
            this(varName, new ArrayList<>());
        }
    }
}
