package com.sleepamos.game.appstates;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Dome;
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

        Dome dome = new Dome(1<<8, 1<<8, 8f);
        Geometry domeGeometry = new Geometry("Ring", dome);

        Box ground = new Box(50.0f, 1.0f,50.0f);
        Geometry groundGeometry = new Geometry("Ground", ground);
        groundGeometry.move(new Vector3f(0f,-2f,0f));

        Material groundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        groundMaterial.setColor("Color", ColorRGBA.Green);
        groundMaterial.setTexture("ColorMap", assetManager.loadTexture("Textures/RockyTexture.jpg")); // with Unshaded.j3md

        groundGeometry.setMaterial(groundMaterial);

        Material domeMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        domeMaterial.setTransparent(true);
        domeMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        domeMaterial.setColor("Color", ColorRGBA.fromRGBA255(10, 10, 10, 200));

        domeGeometry.setMaterial(domeMaterial);
        domeGeometry.setQueueBucket(RenderQueue.Bucket.Transparent);

        this.gameNode.attachChild(domeGeometry);
        this.gameNode.attachChild(groundGeometry);

        DirectionalLight sun = new DirectionalLight(); // This is the light source for shadows
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal()); // Positions the light source
        sun.setColor(ColorRGBA.White);
        this.gameNode.addLight(sun); // Puts the sun in the map

        final int SHADOWMAP_SIZE = 1<<10; // Size of the shadow map
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE,1); // Renders shadows
        dlsr.setLight(sun);
//        this.getApplication().getViewPort().addProcessor(dlsr); // Adds the shadow renderer to the viewport
        this.gameNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); // Make sure everything can have a shadow casted on, but cannot cast a shadow because that will kill fps

//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
//        fpp.addFilter(ssaoFilter);
//        this.getApplication().getViewPort().addProcessor(fpp);
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
