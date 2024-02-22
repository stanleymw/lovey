package com.sleepamos.game;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.app.state.AppState;
import com.jme3.scene.shape.Torus;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class Lovey extends SimpleApplication {

    public Lovey() {
    }

    public Lovey(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        Torus ring = new Torus(128, 128, 0.1f, 5f);
        Geometry geom = new Geometry("Ring", ring);

        Box ground = new Box(999999.0f, 1.0f, 999999.0f);
        Geometry ground_geom = new Geometry("Ground", ground);
        ground_geom.move(new Vector3f(0.0f,-5.0f,0.0f));

        Material ground_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        ground_material.setColor("Color", ColorRGBA.Green);

        ground_geom.setMaterial(ground_material);

        geom.move(new Vector3f(0.0f,0.0f,0.0f));
        geom.rotate(FastMath.HALF_PI, 0, 0);
        geom.scale(1.0f, 1.0f, 9999.0f);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTransparent(true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.setColor("Color", ColorRGBA.fromRGBA255(10, 10, 10, 200));

        geom.setMaterial(mat);
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);

        rootNode.attachChild(geom);
        rootNode.attachChild(ground_geom);

        DirectionalLight sun = new DirectionalLight(); // This is the light source for shadows
        sun.setDirection(new Vector3f(-0.5f, 0.5f, -0.5f).normalizeLocal()); // Positions the light source
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); // Puts the sun in the map

        final int SHADOWMAP_SIZE = 1<<10; // Size of the shadow map
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 1); // Renders shadows
        dlsr.setLight(sun);
//        viewPort.addProcessor(dlsr); // Adds the shadow renderer to the viewport
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); // Make sure everything can have a shadow casted on, but cannot cast a shadow because that will kill fps


    }

    @Override
    public void simpleUpdate(float tpf) {

    }


    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);


        Lovey app = new Lovey();
        app.setSettings(settings);
        app.start();
    }

}
