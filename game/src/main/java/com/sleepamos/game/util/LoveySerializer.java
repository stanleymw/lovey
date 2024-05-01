package com.sleepamos.game.util;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisSerializer;

/**
 * Serialization pipeline: Object with class version -> Serialize data in an easily recoverable format (global storage) -> Deserialize data into recoverable format -> Convert to new Object
 * Object: Class implements LoveySerializable
 * Recoverable format: LoveySerializedClass (Contains variable names, values, reference to superclass LoveySerializedClass which can be serialized.
 * Deserializer uses LoveySerializedClass hierarchy.
 * <p>
 * A custom serialization interface that is able to handle file versioning smoothly.
 */
public class LoveySerializer {
    private static Objenesis objenesis = new ObjenesisSerializer();

    public static String serialize(LoveySerializable s) {
        return serialize(s, (byte) 0);
    }

    public static String serialize(LoveySerializable s, byte version) {
        String serialized = String.valueOf(version);

        return serialized;
    }

    public static LoveySerializable deserialize(String s) {
        return deserialize(s, (byte) 0);
    }

    public static LoveySerializable deserialize(String s, byte expectedVersion) {
        return deserialize(s, expectedVersion, (serialized, fileVersion, eVersion) -> {
            throw new NonFatalException("Unexpected version mismatch, file: " + fileVersion + ", class: " + eVersion);
        });
    }

    public static LoveySerializable deserialize(String s, byte expectedVersion, VersionMismatchedDeserializer onVersionMismatch) {
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
    public interface VersionMismatchedDeserializer {
        LoveySerializable deserialize(String serialized, byte fileVersion, byte expectedVersion);
    }
}
