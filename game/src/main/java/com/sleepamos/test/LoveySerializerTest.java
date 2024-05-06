package com.sleepamos.test;

import com.sleepamos.game.util.FileUtil;
import com.sleepamos.game.util.LoveySerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoveySerializerTest {
    @Test
    void test() {
        final String fileName = "sentir.lovey";
        LoveyTestClass test = new LoveyTestClass();
        LoveySerializer.serialize(fileName, test);
        LoveyTestClass deserialized = LoveySerializer.deserialize(fileName, LoveyTestClass.class);
        assertEquals(test, deserialized);
        FileUtil.deleteFile(fileName);
    }
}