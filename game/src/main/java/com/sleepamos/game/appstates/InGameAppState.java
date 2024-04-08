package com.sleepamos.game.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Dome;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.sun.tools.jconsole.JConsoleContext;

import java.util.Objects;

public class InGameAppState extends BaseAppState {
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Node gameNode;

    private Vector3f directionOnPause;
    private Node shootables;

    @Override
    protected void initialize(Application app) {
        this.assetManager = this.getApplication().getAssetManager();
        this.inputManager = this.getApplication().getInputManager();
        this.rootNode = ((SimpleApplication) this.getApplication()).getRootNode();

        this.setupGameNode();
        this.setEnabled(false);

        this.inputManager.addMapping("Interact",      // Declare...
                new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar, or
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));         // trigger 2: left-button click
    }

    @Override
    protected void cleanup(Application app) {
    }

    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float keyPressed, float tpf) {
            System.out.println(name+keyPressed+tpf);
        }
    };

    final private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            System.out.println(name+keyPressed+tpf);
        }
    };


    /**
     * Sets up a node that contains all game-related spatials.
     */
    private void setupGameNode() {
        this.gameNode = new Node("game_node");
        this.shootables = new Node("shootables");

        Dome dome = new Dome(1<<8, 1<<8, 69f);
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

        for (int i = 0; i < 10; i++) {
            this.shootables.attachChild(makeCube("target" + i, (float) (Math.random() * 10), (float)(Math.random() * 10), (float)(Math.random() * 10)));
        }

        this.gameNode.attachChild(this.shootables);

//        DirectionalLight sun = new DirectionalLight(); // This is the light source for shadows
//        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal()); // Positions the light source
//        sun.setColor(ColorRGBA.White);
//        this.gameNode.addLight(sun); // Puts the sun in the map
//
//        final int SHADOWMAP_SIZE = 1<<10; // Size of the shadow map
//        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE,1); // Renders shadows
//        dlsr.setLight(sun);
////        this.getApplication().getViewPort().addProcessor(dlsr); // Adds the shadow renderer to the viewport
//        this.gameNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); // Make sure everything can have a shadow casted on, but cannot cast a shadow because that will kill fps

//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
//        fpp.addFilter(ssaoFilter);
//        this.getApplication().getViewPort().addProcessor(fpp);
    }

    @Override
    protected void onEnable() {
        this.rootNode.attachChild(this.gameNode);
        this.getApplication().getCamera().lookAtDirection(Objects.requireNonNullElseGet(this.directionOnPause, () -> new Vector3f(0, 1, 0)), new Vector3f(0, 1, 0)); // i love you intellij

        createBinds();
        this.getApplication().getInputManager().addListener(analogListener);
    }

    @Override
    protected void onDisable() {
        this.rootNode.detachChild(this.gameNode);
        this.directionOnPause = this.getApplication().getCamera().getDirection();

        destroyBinds();
    }

    private void createBinds() {
        this.inputManager.addListener(analogListener, "Interact"); // ... and add.
    }

    private void destroyBinds() {
        this.inputManager.removeListener(analogListener);
    }

    @Override
    public void update(float tpf) {
        // run every frame we're enabled
//        for (Spatial i : this.shootables.getChildren()) {
//            i.setLocalScale((float)Math.random());
//        }
    }


    private Geometry makeCube(String name, float x, float y, float z, int size_x, int size_y, int size_z) {
        Box box = new Box(size_x, size_y, size_z);
        Geometry cube = new Geometry(name, box);
        cube.setLocalTranslation(x, y, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.randomColor());
        cube.setMaterial(mat1);
        return cube;
  }

  private Geometry makeCube(String name, float x, float y, float z) {
        return makeCube(name, x, y, z, 1, 1, 1);
  }
}
