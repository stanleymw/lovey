package com.sleepamos.game.interactables;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.Queue;

public class Shootable extends Box implements Interactable {
    public Shootable(int sizeX, int sizeY, int sizeZ) {
    }

    @Override
    public int getPoints() {
        return 0;
    }
}
