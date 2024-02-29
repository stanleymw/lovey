package com.sleepamos.game.appstates;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Torus;
import com.jme3.shadow.DirectionalLightShadowRenderer;

public class InGameAppState extends BaseAppState {
    private AssetManager assetManager;
    private Node rootNode;
    private Node gameNode;

    @Override
    protected void initialize(Application app) {
        this.assetManager = this.getApplication().getAssetManager();
        this.rootNode = ((SimpleApplication) this.getApplication()).getRootNode();

        this.setupGameNode();
        this.setEnabled(false);
    }

    @Override
    protected void cleanup(Application app) {
    }

    /**
     * Sets up a node that contains all game-related spatials.
     */
    private void setupGameNode() {
        this.gameNode = new Node("game_node");

        Torus ring = new Torus(128, 128, 0.1f, 5f);
        Geometry geom = new Geometry("Ring", ring);

        Box ground = new Box(999999.0f, 1.0f, 999999.0f);
        Geometry groundGeom = new Geometry("Ground", ground);
        groundGeom.move(new Vector3f(0.0f,-5.0f,0.0f));

        Material groundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        groundMaterial.setColor("Color", ColorRGBA.Green);

        groundGeom.setMaterial(groundMaterial);

        geom.move(new Vector3f(0.0f,0.0f,0.0f));
        geom.rotate(FastMath.HALF_PI, 0, 0);
        geom.scale(1.0f, 1.0f, 9999.0f);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTransparent(true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.setColor("Color", ColorRGBA.fromRGBA255(10, 10, 10, 200));

        geom.setMaterial(mat);
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);

        this.gameNode.attachChild(geom);
        this.gameNode.attachChild(groundGeom);

        DirectionalLight sun = new DirectionalLight(); // This is the light source for shadows
        sun.setDirection(new Vector3f(-0.5f, 0.5f, -0.5f).normalizeLocal()); // Positions the light source
        sun.setColor(ColorRGBA.White);
        this.gameNode.addLight(sun); // Puts the sun in the map

        final int SHADOWMAP_SIZE = 1<<10; // Size of the shadow map
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 1); // Renders shadows
        dlsr.setLight(sun);
        // viewPort.addProcessor(dlsr); // Adds the shadow renderer to the viewport
        this.gameNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); // Make sure everything can have a shadow casted on, but cannot cast a shadow because that will kill fps
    }

    @Override
    protected void onEnable() {
        this.rootNode.attachChild(this.gameNode);
    }

    @Override
    protected void onDisable() {
        this.rootNode.detachChild(this.gameNode);
    }

    @Override
    public void update(float tpf) {
        // run every frame we're enabled
    }
}
