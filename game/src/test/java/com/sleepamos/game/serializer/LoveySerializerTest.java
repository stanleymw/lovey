package com.sleepamos.game.serializer;

import com.sleepamos.game.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializerTest {
    private static final String fileName = "sentir.lovey";

    @Test
    void test() {
        SerializationClassTest test = new SerializationClassTest();
        LoveySerializer.serialize(fileName, test);
        SerializationClassTest deserialized = LoveySerializer.deserialize(fileName, SerializationClassTest.class);
        assertEquals(test, deserialized);

        FileUtil.deleteFile(fileName);

        SerializationSubclassTest subclassTest = new SerializationSubclassTest();
        LoveySerializer.serialize(fileName, subclassTest);
        SerializationSubclassTest deserializedSubclassTest = LoveySerializer.deserialize(fileName, SerializationSubclassTest.class);
        assertEquals(subclassTest, deserializedSubclassTest);
    }

    @AfterEach
    void tearDown() {
        FileUtil.deleteFile(fileName);
    }
}