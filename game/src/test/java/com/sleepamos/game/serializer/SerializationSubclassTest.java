package com.sleepamos.game.serializer;

import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;

@SuppressWarnings("serial")
class SerializationSubclassTest extends SerializationClassTest {
    @LoveySerializableClassVersion
    private static final byte VERSION = 11;

    @LoveySerializableValue("yahhoo")
    private final double yippee = 3.5;

    private final long goodNameSchemeGuys = 6;

    @Override
    public boolean equals(Object o) {
        //noinspection ConstantValue
        return super.equals(o) && yippee == ((SerializationSubclassTest) o).yippee && goodNameSchemeGuys == ((SerializationSubclassTest) o).goodNameSchemeGuys;
    }
}
