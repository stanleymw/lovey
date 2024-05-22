package com.sleepamos.game.util;

import com.sleepamos.game.exceptions.NonFatalException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public final class FileUtil {
    private FileUtil() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static <T> T readSerializedObjectFromFile(String fileName, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromFile(fileName));
    }

    public static <T> T readSerializedObjectFromFile(File file, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromFile(file));
    }

    @SneakyThrows
    public static Object readSerializedObjectFromFile(String fileName) {
        return fromFileInputStream(new FileInputStream(fileName));
    }

    @SneakyThrows
    public static Object readSerializedObjectFromFile(File file) {
        return fromFileInputStream(new FileInputStream(file));
    }

    /**
     * If this crashes, it's your fault not mine
     */
    public static <T> T readSerializedObjectFromInputStream(InputStream inputStream, Class<T> clazz) {
        return clazz.cast(readSerializedObjectFromInputStream(inputStream));
    }

    /**
     * If this crashes, it's your fault not mine
     */
    @SneakyThrows
    @Nullable
    public static Object readSerializedObjectFromInputStream(InputStream is) {
        try (ObjectInputStream in = new ObjectInputStream(is)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new NonFatalException("Error reading a serialized object from stream " + is, e);
        } finally {
            is.close();
        }
    }

    @SneakyThrows
    @Nullable
    private static Object fromFileInputStream(FileInputStream fis) {
        try {
            if (fis.getChannel().size() == 0) {
                return null;
            }
        } catch (Exception e) {
            throw new NonFatalException("can this even throw an error", e);
        }
        try (ObjectInputStream in = new ObjectInputStream(fis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new NonFatalException("Error reading a serialized object from stream " + fis, e);
        } finally {
            fis.close();
        }
    }

    public static void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(new File(fileName).toPath());
        } catch (IOException e) {
            throw new NonFatalException(e);
        }
    }

    public static Path getWorkingDirectory() { // https://stackoverflow.com/a/46246765
        return FileSystems.getDefault().getPath(".").toAbsolutePath();
    }

    public static String[] getDirectoryNames(Path dir, Function<File, Boolean> check) { // https://stackoverflow.com/a/5125258
        File file = dir.toFile();
        return file.list((current, name) -> {
            File f = new File(current, name);
            return f.isDirectory() && check.apply(f);
        });
    }

    public static boolean exists(Path p) {
        return p.toFile().exists();
    }

    public static String getFromResources(String path) {
        try (InputStream stream = getInputStreamFromResources(path)) {
            if (stream == null) {
                return "";
            }
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            throw new NonFatalException("error loading from resources folder", e);
        }
    }

    public static InputStream getInputStreamFromResources(String path) {
        // System.out.println("reading from: " + path);
        return FileUtil.class.getResourceAsStream(path);
    }
}
