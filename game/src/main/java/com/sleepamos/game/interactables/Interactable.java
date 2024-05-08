package com.sleepamos.game.interactables;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.serializer.LoveySerializable;

@SuppressWarnings("serial")
public abstract class Interactable extends Geometry implements LoveySerializable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    public Interactable(String name, Mesh mesh) {
        super(name, mesh);
    }
    public abstract void onInteract();
    public abstract void onInteractStop();
}
