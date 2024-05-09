package com.sleepamos.game.interactables;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class Slider extends Interactable {
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
