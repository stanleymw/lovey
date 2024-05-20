package com.sleepamos.game.interactables;

import com.jme3.scene.Mesh;
import com.sleepamos.game.game.GameState;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

@SuppressWarnings("serial")
public class Shootable extends Interactable {
    @LoveySerializableClassVersion
    private static final byte VERSION = 10;

    private final long points;
    private final double angleX, angleZ;
    private GameState gameState;

    public Shootable(String name, Mesh mesh, GameState gs, double angle_x, double angle_z, long pts) {
        super(name, mesh);

        this.points = pts;
        this.gameState = gs;

        this.angleX = angle_x;
        this.angleZ = angle_z;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public void setGameState(GameState gs) {
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
