package com.sleepamos.game.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilTest {
    @Test
    void testGetWorkingDirectory() {
        assertEquals(FileUtil.getWorkingDirectory(), FileUtil.getWorkingDirectory()); // There's not really a way to test this? Just change it to Path.of("test") to examine output.
    }
}