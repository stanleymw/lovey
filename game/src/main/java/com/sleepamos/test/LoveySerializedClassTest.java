package com.sleepamos.test;

import com.sleepamos.game.util.LoveySerializable;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializedClassTest {
    private static class LoveyClass implements LoveySerializable {
        private final int i = 2;
        private final String s = "test";

        private final int[] iA = {2, 4, 6, 8};

        private final List<String> stringList = new ArrayList<>();

        public LoveyClass() {
            stringList.add("a");
            stringList.add("b");
            stringList.add("c");
        }

        @Override
        public boolean equals(Object o) {
            //noinspection ConstantValue
            return o instanceof LoveyClass other &&
                    this.i == other.i &&
                    this.s.equals(other.s) &&
                    Arrays.equals(this.iA, other.iA) &&
                    this.stringList.equals(other.stringList);
        }
    }

    @Test
    void testSerialization() throws IOException {
        final String fileName = "LoveySerializedClassTest.java.txt";
        LoveyClass loveyPreSerializedA = new LoveyClass();

        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(loveyPreSerializedA);
            outputStream.flush();
            outputStream.close();

            try(FileInputStream fileInputStream = new FileInputStream(fileName)) {
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                LoveyClass loveyDeserializedA = (LoveyClass) inputStream.readObject();
                inputStream.close();

                assertEquals(loveyPreSerializedA, loveyDeserializedA);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } finally {
            File f = new File(fileName);
            f.deleteOnExit();
        }
    }
}