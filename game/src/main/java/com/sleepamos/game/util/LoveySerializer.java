package com.sleepamos.game.util;

import java.util.List;

/**
 * A custom serialization interface that is able to handle file versioning smoothly.
 * The serializer first creates a set of class definitions with the layouts of each class being serialized, then writes serialized data
 * following the defined formats.
 * Arrays and objects extending {@link List} will have only their contents serialized.
 * <p>
 * Ex:
 * Given the following class A to be serialized:
 * {@snippet lang = "java":
 * public class A implements LoveySerializable {
 *     public static final byte VERSION = 10;
 *
 *     private double d;
 *     private double e;
 *     private boolean bool;
 *     private B b; // B implements LoveySerializable
 *     private transient C c; // C does not implement LoveySerializable
 *     private transient B anotherB; // B implements LoveySerializable
 *     private C c; // this would issue a warning on serialize if uncommented since C does not implement LoveySerializable. C would not be included in the final serialization.
 *     private List<B> bList; // Only the contents of the list will be serialized.
 *     // Class continues...
 * }
 *
 * public class B implements LoveySerializable {
 *     private double bSubVar;
 * }
 * public class C {}
 * }
 * The format of the serialized file would be similar to this format, without line breaks or information in parentheses:
 * <pre>
 * type A: (A must be of the root type being serialized, any following class structure definitions after A are for included sub-elements)
 *     version VERSION (the value, stored as a byte) (note: this value will NOT be read from the class; instead it must be passed in to the serializer)
 *     double;
 *     double;
 *     boolean;
 *     val B; (val indicates an {@link Object} that will need to be defined)
 *     val List type=B len=bList.size()
 * fin;
 * type B:
 *     version VERSION (the value)
 *     double;
 * fin;
 * val root:
 *     d
 *     e
 *     bool
 *     subval b:
 *         bSubVar
 *     fin;
 *     subval bList:
 *         bList.get(0)
 *         ... until all elements of bList
 *     fin;
 * fin;
 * end file;
 * </pre>
 *
 */
public class LoveySerializer {
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
            throw new NonFatalException();
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

    /**
     * Serializes class definitions. The class will be serialized in order.
     */
    private static String serializeClass(Class<?> clazz) {
        return "";
    }
}
