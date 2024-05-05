package com.sleepamos.game.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class FileUtil {
    private FileUtil() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static <T> T readSerializedObjectFromFile(String fileName, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromFile(fileName));
    }

    public static Object readSerializedObjectFromFile(String fileName) {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return in.readObject();
        } catch(IOException | ClassNotFoundException e) {
            throw new NonFatalException("Error reading a serialized object from file " + fileName, e);
        }
    }
}
