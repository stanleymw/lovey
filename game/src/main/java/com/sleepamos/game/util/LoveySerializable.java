package com.sleepamos.game.util;

import java.io.InvalidClassException;

/**
 * A custom serialization interface that is able to handle file versioning.
 */
public interface LoveySerializable {
    static String serialize(LoveySerializable s, byte version) {
        String serialized = String.valueOf(version);

        return serialized;
    }

    static LoveySerializable deserialize(String s, byte expectedVersion) {
        return deserialize(s, expectedVersion, () -> {
            throw new NonFatalException();
        });
    }

    static LoveySerializable deserialize(String s, byte expectedVersion, VersionMismatchedSerializer onVersionMismatch) {
        byte version = s.getBytes()[0];
        if(version != expectedVersion) {

        }

        return null;
    }

    @FunctionalInterface
    interface VersionMismatchedSerializer {
        String serialize();
    }
}
