package com.sleepamos.game.beatmap;

import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.serializer.LoveySerializable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
class InteractableSpawner implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    private final ArrayList<Spawn> toSpawn;

    public InteractableSpawner() {
        this.toSpawn = new ArrayList<>();
    }

    public List<Spawn> getTargetsToSpawn() {
        return toSpawn;
    }
}
