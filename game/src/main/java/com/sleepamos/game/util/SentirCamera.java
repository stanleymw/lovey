package com.sleepamos.game.util;

import com.jme3.app.FlyCamAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class SentirCamera extends FlyByCamera {
    private float rotationSpeed = 1;
    /**
     * Creates a new FlyByCamera to control the specified camera.
     *
     * @param cam camera to be controlled (not null)
     */
    public SentirCamera(Camera cam, float rotSpeed) {
        super(cam);
        this.rotationSpeed = rotSpeed;
    }

    public void rotateCamera(float value, Vector3f axis) {
        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        // Hook to Prevent back-flips (degree > 180)
        if (up.getY() < 0) {
            return;
        }

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();
        cam.setAxes(q);
    }
}
