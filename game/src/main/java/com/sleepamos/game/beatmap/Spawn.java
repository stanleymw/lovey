package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.LoveySerializable;

public record Spawn(double impactTime, Interactable interactable, double speed) implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;
}
