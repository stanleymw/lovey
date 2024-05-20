package com.sleepamos.game.serializer;

import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
class SerializationClassTest implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    @LoveySerializableValue("not i")
    private final int i = 2;

    @LoveySerializableValue("s")
    private final String s = "test";

    private final int[] iA = {2, 4, 6, 8};

    private final List<String> stringList = new ArrayList<>();

    @LoveySerializableValue("otherTest")
    private final OtherSerializationClassTest otherTest = new OtherSerializationClassTest();

    public SerializationClassTest() {
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
    }

    @Override
    public boolean equals(Object o) {
        //noinspection ConstantValue
        return this == o ||
                o instanceof SerializationClassTest other &&
                        this.i == other.i &&
                        this.s.equals(other.s) &&
                        Arrays.equals(this.iA, other.iA) &&
                        this.stringList.equals(other.stringList) &&
                        otherTest.equals(other.otherTest);
    }
}
