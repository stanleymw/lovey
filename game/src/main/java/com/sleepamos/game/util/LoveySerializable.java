package com.sleepamos.game.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom serialization interface that is able to handle file versioning smoothly.
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
