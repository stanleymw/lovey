package com.sleepamos.game.serializer;

import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;

@SuppressWarnings("serial")
class OtherSerializationClassTest implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 12;

    @LoveySerializableValue("timbuktu")
    private final long timbuktu = 3000000;

    @Override
    public boolean equals(Object o) {
        //noinspection ConstantValue
        return this == o ||
                o instanceof OtherSerializationClassTest other &&
                        other.timbuktu == timbuktu;
    }
}
