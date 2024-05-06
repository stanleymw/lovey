package com.sleepamos.test;

import com.sleepamos.game.util.FileUtil;
import com.sleepamos.game.util.LoveySerializedClass;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoveySerializedClassTest {
    @Test
    void testSerialization() throws IOException {
        final String fileName = "LoveySerializedClassTest.java.txt";
        LoveyTestClass loveyPreSerializedA = new LoveyTestClass();

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
        } finally {
            FileUtil.deleteFile(fileName);
        }
    }
}