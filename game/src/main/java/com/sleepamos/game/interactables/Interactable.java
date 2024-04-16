package com.sleepamos.game.interactables;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public abstract class Interactable extends Geometry {
    public Interactable(String name, Mesh mesh) {
        super(name, mesh);
    }
    public abstract void onInteract();
    public abstract void onInteractStop();
}
