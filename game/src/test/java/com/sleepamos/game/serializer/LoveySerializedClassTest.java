package com.sleepamos.game.serializer;

import com.sleepamos.game.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializedClassTest {
    private static final String fileName = "LoveySerializedClassTest.java.txt";
    @Test
    void testSerialization() throws IOException {
        SerializationClassTest loveyPreSerializedA = new SerializationClassTest();

        LoveySerializedClass loveySerializedA = new LoveySerializedClass(loveyPreSerializedA);

        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(loveySerializedA);
            outputStream.flush();
            outputStream.close();

            try(FileInputStream fileInputStream = new FileInputStream(fileName)) {
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                LoveySerializedClass loveyDeserializedA = (LoveySerializedClass) inputStream.readObject();
                inputStream.close();

                assertEquals(loveySerializedA, loveyDeserializedA);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AfterEach
    void tearDown() {
        FileUtil.deleteFile(fileName);
    }
}