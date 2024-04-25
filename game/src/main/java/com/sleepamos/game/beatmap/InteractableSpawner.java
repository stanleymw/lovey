package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;

import java.util.ArrayList;

public class InteractableSpawner {
    private final ArrayList<Spawn> toSpawn;

    public InteractableSpawner() {
        this.toSpawn = new ArrayList<>();
    }

    private record Spawn(Interactable interactable, double time) {}
}
