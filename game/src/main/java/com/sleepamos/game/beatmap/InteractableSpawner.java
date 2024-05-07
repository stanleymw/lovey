package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.util.serializer.LoveySerializable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class InteractableSpawner implements LoveySerializable {
    private final ArrayList<Spawn> toSpawn;

    public InteractableSpawner() {
        this.toSpawn = new ArrayList<>();
    }

    public List<Spawn> getTargetsToSpawn() {
        return toSpawn;
    }
}
