package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.LoveySerializable;

public record Spawn(Interactable interactable, double hitTime, double reactionTime) implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;
}
