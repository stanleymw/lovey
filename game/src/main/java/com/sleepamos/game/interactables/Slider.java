package com.sleepamos.game.interactables;
import com.jme3.scene.Mesh;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

@SuppressWarnings("serial")
public class Slider extends Interactable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    public Slider(String name, Mesh mesh) {
        super(name, mesh);
    }

    @Override
    public void onInteract() {
        System.out.println("GETTING POINTS");
    }

    @Override
    public void onInteractStop() {
        System.out.println("NO MAS PUNTOS");
    }
}
