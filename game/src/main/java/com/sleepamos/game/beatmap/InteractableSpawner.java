package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.util.LoveySerializable;

import java.util.ArrayList;

public class InteractableSpawner implements LoveySerializable {
    private final ArrayList<Spawn> toSpawn;

    public InteractableSpawner() {
        this.toSpawn = new ArrayList<>();
    }

    private record Spawn(Interactable interactable, double time) implements LoveySerializable {} // note: use Class.getRecordComponents()
}
