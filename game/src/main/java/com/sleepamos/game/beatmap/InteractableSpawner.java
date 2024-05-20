package com.sleepamos.game.beatmap;

import com.sleepamos.game.serializer.LoveySerializable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class InteractableSpawner implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    private final List<Spawn> toSpawn;

    public InteractableSpawner() {
        this.toSpawn = new ArrayList<>();
    }

    public InteractableSpawner(List<Spawn> stuffToSpawn) {
        this.toSpawn = stuffToSpawn;
    }

    public List<Spawn> getTargetsToSpawn() {
        return toSpawn;
    }
}
