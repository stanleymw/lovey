package com.sleepamos.game.interactables;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.sleepamos.game.game.GameState;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

@SuppressWarnings("serial")
public class Shootable extends Interactable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    private final long points;
    private final float angleX;
    private final float angleZ;

    private boolean willGivePointsIfShot = false;
    private GameState gameState;

    public Shootable(String name, Mesh mesh, GameState gs, float angle_x, float angle_z, long pts) {
        super(name, mesh);

        this.points = pts;
        this.gameState = gs;

        this.angleX = angle_x;
        this.angleZ = angle_z;
    }

    public float getAngleX() {
        return angleX;
    }

    public void setIfWillGivePoints(boolean n) {
        this.willGivePointsIfShot = n;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    @Override
    public void onInteract() {
        if (this.willGivePointsIfShot) {
            this.gameState.addPoints(this.points);
            System.out.println("add " + this.points + " pts");
        }

        this.removeFromParent(); // destroy
    }

    @Override
    public void onInteractStop() {
        System.out.println("STOPPED ITERACT");
    }
}
