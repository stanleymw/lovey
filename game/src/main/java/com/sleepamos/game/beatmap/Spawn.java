package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.serializer.LoveySerializable;

public record Spawn(Interactable interactable, double time) implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;
}
