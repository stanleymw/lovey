package com.sleepamos.game.util;

import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;

public final class FileUtil {
    private FileUtil() {
        throw new UnsupportedOperationException("Utility Class");
    }

    @SneakyThrows
    public static <T> T readSerializedObjectFromFile(String fileName, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromFile(fileName));
    }

    @SneakyThrows
    public static <T> T readSerializedObjectFromFile(File file, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromFile(file));
    }

    public static Object readSerializedObjectFromFile(String fileName) throws FileNotFoundException {
        return fromFileInputStream(new FileInputStream(fileName));
    }

    public static Object readSerializedObjectFromFile(File file) throws FileNotFoundException {
        return fromFileInputStream(new FileInputStream(file));
    }


    private static Object fromFileInputStream(FileInputStream fis) {
        try(ObjectInputStream in = new ObjectInputStream(fis)) {
            return in.readObject();
        } catch(IOException | ClassNotFoundException e) {
            throw new NonFatalException("Error reading a serialized object from stream " + fis, e);
        }
    }

    public static void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(new File(fileName).toPath());
        } catch(IOException e) {
            throw new NonFatalException(e);
        }
    }
}
