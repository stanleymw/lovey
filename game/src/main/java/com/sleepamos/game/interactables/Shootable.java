package com.sleepamos.game.interactables;

import com.jme3.scene.Mesh;
import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;

@SuppressWarnings("serial")
public class Shootable extends Interactable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    private final int points;

    public Shootable(String name, Mesh mesh, int pts) {
        super(name, mesh);
        points = pts;
    }

    @Override
    public void onInteract() {
        System.out.println("add " + this.points + " pts");
        this.removeFromParent();
    }

    @Override
    public void onInteractStop() {
        System.out.println("INTERACTION STOOPPED");
    }
}
