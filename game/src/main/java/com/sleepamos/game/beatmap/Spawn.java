package com.sleepamos.game.beatmap;

import com.sleepamos.game.serializer.LoveySerializable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

public record Spawn(float xAngleRad, float zAngleRad, double hitTime, double reactionTime) implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;
}
