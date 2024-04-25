package com.sleepamos.game.interactables;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.Queue;

public class Shootable extends Interactable {
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
