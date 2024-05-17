package com.sleepamos.game.interactables;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.sleepamos.game.game.GameState;

import java.util.Queue;

public class Shootable extends Interactable {
    private final long points;
    private GameState gameState;

    public Shootable(String name, Mesh mesh, GameState gs, double angle_x, double angle_z, long pts) {
        super(name, mesh);

        this.points = pts;
        this.gameState = gs;
    }

    @Override
    public void onInteract() {
        System.out.println("add " + this.points + " pts");
        this.gameState.addPoints(this.points);

        this.removeFromParent(); // destroy
    }

    @Override
    public void onInteractStop() {
        System.out.println("STOPPED ITERACT");
    }
}
