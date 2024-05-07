package com.sleepamos.test;

import com.sleepamos.game.util.FileUtil;
import com.sleepamos.game.util.serializer.LoveySerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializerTest {
    private static final String fileName = "sentir.lovey";

    @Test
    void test() {
        LoveyTestClass test = new LoveyTestClass();
        LoveySerializer.serialize(fileName, test);
        LoveyTestClass deserialized = LoveySerializer.deserialize(fileName, LoveyTestClass.class);
        assertEquals(test, deserialized);

        FileUtil.deleteFile(fileName);

        LoveySubclassTestClass subclassTest = new LoveySubclassTestClass();
        LoveySerializer.serialize(fileName, subclassTest);
        LoveySubclassTestClass deserializedSubclassTest = LoveySerializer.deserialize(fileName, LoveySubclassTestClass.class);
        assertEquals(subclassTest, deserializedSubclassTest);
    }

    @AfterEach
    void tearDown() {
        FileUtil.deleteFile(fileName);
    }
}