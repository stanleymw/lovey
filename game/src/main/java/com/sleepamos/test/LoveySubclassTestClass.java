package com.sleepamos.test;

import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.annotations.LoveySerializableValue;

class LoveySubclassTestClass extends LoveyTestClass {
    @LoveySerializableClassVersion
    private static final byte VERSION = 11;

    @LoveySerializableValue("yahhoo")
    private final double yippee = 3.5;

    private final long goodNameSchemeGuys = 6;

    @Override
    public boolean equals(Object o) {
        //noinspection ConstantValue
        return super.equals(o) && yippee == ((LoveySubclassTestClass) o).yippee && goodNameSchemeGuys == ((LoveySubclassTestClass) o).goodNameSchemeGuys;
    }
}
