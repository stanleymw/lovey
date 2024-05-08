package com.sleepamos.game.util.serializer;

import com.sleepamos.game.util.FileUtil;
import com.sleepamos.game.util.NonFatalException;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisSerializer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * A custom serialization interface that is able to handle file versioning smoothly. Conversions between different versions is supported by passing in a handler method.
 */
public class LoveySerializer {
    private static final Objenesis objenesis = new ObjenesisSerializer();

    public static void serialize(String fileName, LoveySerializable obj) {
        LoveySerializedClass serializedClass = new LoveySerializedClass(obj);
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(serializedClass);
            outputStream.flush();
            outputStream.close();
        } catch(Exception e) {
            throw new NonFatalException(e);
        }
    }

    public static <T> T deserialize(String fileName, Class<T> clazz) {
        return clazz.cast(deserialize(fileName, clazz, (serialized, fileVersion, eVersion, c, obj) -> {
            throw new NonFatalException("Unexpected version mismatch, stored file version: " + fileVersion + ", class defined version: " + eVersion);
        }));
    }

    public static <T> T deserialize(String fileName, Class<T> clazz, VersionMismatchedDeserializer onVersionMismatch) {
        LoveySerializedClass deserialized = FileUtil.readSerializedObjectFromFile(fileName, LoveySerializedClass.class);

        if(!clazz.isAssignableFrom(deserialized.getStoredClazz())) {
            throw new NonFatalException("Deserialized class of type " + deserialized.getStoredClazz().getName() + " is not assignable to requested class: " + clazz.getName());
        }

        Class<?> storedClazz = deserialized.getStoredClazz();

        Object obj = objenesis.newInstance(storedClazz);

        LoveySerializedClass currentDeserializeCandidate = deserialized;
        Class<?> toDeserialize = deserialized.getStoredClazz();

        try {
            while(!currentDeserializeCandidate.isRootClass()) { // handle the superclass values as well
                byte serializedVer = currentDeserializeCandidate.getVersion();
                byte definedVer = LoveySerializationUtil.getClassVersion(toDeserialize);

                // System.out.println("serialized version: " + serializedVer + ", defined version: " + definedVer + ", equal? " + (serializedVer == definedVer));

                // if serializedVer == definedVer then run
                // if serializedVer != definedVer and deserialize decides not to cancel (returns false) then run
                if(serializedVer == definedVer || !onVersionMismatch.deserialize(currentDeserializeCandidate, serializedVer, definedVer, toDeserialize, obj)) {
                    Map<String, String> serializedNameToClassName = LoveySerializationUtil.serializedNameToClassName(toDeserialize);
                    // iterate over all values in the deserialized class and put them into obj
                    for (LoveySerializedClassDataEntry entry : currentDeserializeCandidate.getData()) {
                        System.out.println("attempting to retrieve: " + entry.serializedName() + " from " + toDeserialize);
                        Field f = toDeserialize.getDeclaredField(serializedNameToClassName.get(entry.serializedName()));
                        f.setAccessible(true);
                        f.set(obj, entry.data());
                    }
                }

                currentDeserializeCandidate = currentDeserializeCandidate.getSuperclass();
                toDeserialize = currentDeserializeCandidate.getStoredClazz();
            }
        } catch(Exception e) {
            throw new NonFatalException("Error while deserializing", e);
        }

        return clazz.cast(obj);
    }

    /**
     * Handles the conversion from a LoveySerializable serialized in a different (prior) version to the latest version.
     */
    @FunctionalInterface
    public interface VersionMismatchedDeserializer {
        /**
         * Handles a version conversion for a specified class by deserializing data into the deserialization object.
         * @param serialized The serialized data.
         * @param fileVersion The version of the serialized data.
         * @param expectedVersion The version defined in the class.
         * @param clazz The class with a version mismatch.
         * @param o The object that data is being deserialized into.
         * @return Whether further deserialization should be canceled. If true, this method should handle ALL deserialization for all fields included in serialized.
         *         If false, this method should handle NONE of the deserialization for any fields defined in serialized.
         *         If only new fields were added between version changes, false would be a return option since this method would only need to intiialize the new fields.
         *         If fields were deleted or more complex changes were made, this method may need to return true and handle the complex logic.
         */
        boolean deserialize(LoveySerializedClass serialized, byte fileVersion, byte expectedVersion, Class<?> clazz, Object o);
    }
}
