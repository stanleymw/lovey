package com.sleepamos.game.appstates;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.beatmap.Spawn;
import com.sleepamos.game.game.GameState;
import com.sleepamos.game.gui.screen.GameEndScreen;
import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.interactables.Shootable;

import java.util.HashMap;
import java.util.List;

public class InGameAppState extends BaseAppState {
    private final double graceTime = 0.5; // if the target isn't shot within graceTime seconds after it becomes Red, it
    private final Beatmap map;
    private final HashMap<Spawn, Interactable> interactables = new HashMap<>();
    private final List<Spawn> spawners;
    private GameState gameState;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node rootNode;
    private Node gameNode;
    private BitmapText crosshair;
    private Node shootables;
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            // // System.out.println("ACTION:" + name+ " | " + keyPressed+ " | " +tpf);
            //noinspection SwitchStatementWithTooFewBranches
            switch (name) {
                case "Interact" -> {
                    gameState.setInteracting(keyPressed);
                    Interactable hit = null;

                    CollisionResults collisionResult = castRay(shootables);
                    if (collisionResult.size() > 0) {
                        hit = (Interactable) collisionResult.getClosestCollision().getGeometry();
                    }

                    gameState.setInteractingWith(hit);
                }
            }
        }
    };
    // will be destroyed and user will get no points
    private AudioNode audioNode;
    private double clock = 0;
    private int spawnWindowLeft = 0;
    private int spawnWindowRight = 0;

    public InGameAppState(Beatmap m) {
        super();

        this.map = m;
        this.spawners = this.map.getSpawner().getTargetsToSpawn();
    }

    public InGameAppState(Beatmap m, AudioNode aud) {
        this(m);

        this.audioNode = aud;
    }

    @Override
    protected void initialize(Application app) {
        // System.out.println("INITIALIZING");
        this.assetManager = this.getApplication().getAssetManager();
        this.inputManager = this.getApplication().getInputManager();
        this.gameState = new GameState();
        this.rootNode = ((SimpleApplication) this.getApplication()).getRootNode();

        this.setupGameNode();
        this.gameNode.attachChild(this.audioNode);
        // this.setEnabled(true);
        this.rootNode.attachChild(this.gameNode);

        this.initCrossHairs();

        // set camera position to 0 xAngle, elevated zAngle
        this.getApplication().getCamera().lookAtDirection(new Vector3f(1f, 0.3f, 0f), new Vector3f(0, 1, 0));

        this.inputManager.addMapping("Interact", // Declare...
                new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar, or
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click

        // System.out.println("DONE INITIALIZING");
    }

    @Override
    protected void cleanup(Application app) {
        // System.out.println("Cleanup called");
        destroyBinds();

        this.audioNode.stop();

        Lovey.getInstance().getGuiNode().detachChild(crosshair);

        this.inputManager.deleteMapping("Interact");
        this.rootNode.detachChild(this.gameNode);
    }

    /**
     * Sets up a node that contains all game-related spatials.
     */
    private void setupGameNode() {
        this.gameNode = new Node("game_node");
        this.shootables = new Node("shootables");

        // Dome dome = new Dome(1 << 8, 1 << 8, 50.0f);
        // Geometry domeGeometry = new Geometry("Ring", dome);

        Box ground = new Box(50.0f, 1.0f, 50.0f);
        Geometry groundGeometry = new Geometry("Ground", ground);
        groundGeometry.move(new Vector3f(0f, -2f, 0f));

        Material groundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        groundMaterial.setColor("Color", ColorRGBA.Green);
        groundMaterial.setTexture("ColorMap", assetManager.loadTexture("Textures/RockyTexture.jpg")); // with
        // Unshaded.j3md
        groundGeometry.setMaterial(groundMaterial);

        // Material domeMaterial = new Material(assetManager,
        // "Common/MatDefs/Misc/Unshaded.j3md");
        // domeMaterial.setTransparent(true);
        // domeMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        // domeMaterial.setColor("Color", ColorRGBA.fromRGBA255(10, 10, 10, 220));
        //
        // domeGeometry.setMaterial(domeMaterial);
        // domeGeometry.setQueueBucket(RenderQueue.Bucket.Transparent);

        // this.gameNode.attachChild(domeGeometry);
        this.gameNode.attachChild(groundGeometry);

        //
        // for (int i = 0; i < 10; i++) {
        // this.shootables.attachChild(makeShootable("target" + i, (float)
        // (Math.random() * 10),
        // (float) (Math.random() * 10), (float) (Math.random() * 10)));
        // }

        this.gameNode.attachChild(this.shootables);

        // DirectionalLight sun = new DirectionalLight(); // This is the light source
        // for shadows
        // sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal()); //
        // Positions the light source
        // sun.setColor(ColorRGBA.White);
        // this.gameNode.addLight(sun); // Puts the sun in the map
        //
        // final int SHADOWMAP_SIZE = 1<<10; // Size of the shadow map
        // DirectionalLightShadowRenderer dlsr = new
        // DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE,1); // Renders
        // shadows
        // dlsr.setLight(sun);
        //// this.getApplication().getViewPort().addProcessor(dlsr); // Adds the shadow
        // renderer to the viewport
        // this.gameNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); // Make
        // sure everything can have a shadow casted on, but cannot cast a shadow because
        // that will kill fps

        // FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        // SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        // fpp.addFilter(ssaoFilter);
        // this.getApplication().getViewPort().addProcessor(fpp);
    }

    @Override
    protected void onEnable() {
        // System.out.println("onEnable Called");
        this.getStateManager().getState(FlyCamAppState.class).setEnabled(true);

        createBinds();
        if (this.audioNode != null) {
            this.audioNode.play();
        }
    }

    @Override
    protected void onDisable() {
        // System.out.println("onDisable Called");

        this.getStateManager().getState(FlyCamAppState.class).setEnabled(false);
        destroyBinds();

        if (this.audioNode != null) {
            this.audioNode.pause();
        }
    }

    private void createBinds() {
        this.inputManager.addListener(actionListener, "Interact"); // ... and add.
    }

    private void destroyBinds() {
        this.inputManager.removeListener(actionListener);
    }

    // 30 ticks per second
    // Seconds/Frame -> Frame/second

    @Override
    public void update(float tpf) {
        clock += tpf;
        // run every frame we're enabled
        handleSpawns();
        handleInteraction();
    }

    private void handleSpawns() {
        if (spawnWindowLeft >= spawners.size()) {
            // quit
            Lovey.getInstance().getScreenHandler().hideLastShownScreen();

            Lovey.getInstance().useGUIBehavior(true);
            Lovey.getInstance().getScreenHandler().showScreen(new GameEndScreen(this.gameState.getPoints(), this.map, this.audioNode));
            Lovey.getInstance().exitMap();
            return;
        }
        // ensure that things targets are spawned properly
        // maintain a sliding window of alive objects
        // private int spawnWindowRight = 1;
        // private int spanWindowLeft = 0;
        // private List<Spawn> spawners;
        if (spawnWindowRight < spawners.size()) {
            if (clock >= spawners.get(spawnWindowRight).hitTime() - spawners.get(spawnWindowRight).reactionTime()) {
                Spawn current = spawners.get(spawnWindowRight);

                Sphere sph = new Sphere(20, 20, 1);
                Shootable shot = new Shootable("Target", sph, this.gameState, current.xAngleRad(), current.zAngleRad(), 10);
                Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat1.setColor("Color", ColorRGBA.fromRGBA255(255, 0, 0, 20));
                mat1.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                shot.setMaterial(mat1);
                shot.setQueueBucket(RenderQueue.Bucket.Transparent);

                interactables.put(current, shot);
                this.shootables.attachChild(shot);

                // we must spawn this
                spawnWindowRight++;
            }
        }

        Spawn left = spawners.get(spawnWindowLeft);
        if (clock >= left.hitTime() + graceTime) {
            (interactables.get(left)).removeFromParent();
            spawnWindowLeft++;
        }

        for (int i = spawnWindowLeft; i < spawnWindowRight; i++) {
            Spawn current = spawners.get(i);

            double domeRadius = 50.0d; // change this when domeRadius is actually implemented
            double curRadius = domeRadius - (clock - current.hitTime()) * domeRadius;

            Shootable shot = (Shootable) interactables.get(current);

            if (clock >= current.hitTime()) {
                shot.getMaterial().setColor("Color", ColorRGBA.Green);
                shot.setIfWillGivePoints(true);
            }

            shot.setLocalTranslation(FastMath.cos(shot.getAngleX()) * (float) curRadius, FastMath.sin(shot.getAngleZ()) * (float) curRadius, FastMath.sin(shot.getAngleX()) * (float) curRadius);

        }
    }

    private void handleInteraction() {
        if (gameState.isInteracting()) {
            // we need to raycast every frame while Interacting (maybe could optimize this)
            Interactable hit = null;

            CollisionResults collisionResult = castRay(shootables);
            if (collisionResult.size() > 0) {
                hit = (Interactable) collisionResult.getClosestCollision().getGeometry();
            }

            // lets check
            if (gameState.getInteractingWith() != hit) {
                // we moved off, STOP INTERACTING
                gameState.setInteracting(false);
                gameState.setInteractingWith(null);
            }

            if (gameState.getInteractingWith() != null) {
                gameState.getInteractingWith().onInteract();
            }
        }
    }

    private void initCrossHairs() {
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        this.crosshair = new BitmapText(guiFont);
        crosshair.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        crosshair.setText("+"); // crosshair
        crosshair.setLocalTranslation( // center
                (float) Lovey.getInstance().getSettings().getWidth() / 2 - crosshair.getLineWidth() / 2, (float) Lovey.getInstance().getSettings().getHeight() / 2 + crosshair.getLineHeight() / 2, 0);
        Lovey.getInstance().getGuiNode().attachChild(crosshair);
    }

    // private Interactable makeShootable(String name, float x, float y, float z,
    // int size_x, int size_y, int size_z) {
    // Box box = new Box(size_x, size_y, size_z);
    // Shootable cube = new Shootable(name, box, this.gameState, 0.1, 0.1, 10);
    // cube.setLocalTranslation(x, y, z);
    //
    // Material mat1 = new Material(assetManager,
    // "Common/MatDefs/Misc/Unshaded.j3md");
    // mat1.setColor("Color", ColorRGBA.randomColor());
    // cube.setMaterial(mat1);
    //
    // return cube;
    // }

    // private Interactable makeShootable(String name, float x, float y, float z) {
    // return makeShootable(name, x, y, z, 1, 1, 1);
    // }

    private CollisionResults castRay(Node whitelist) {
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from cam loc to cam direction.
        Camera cam = this.getApplication().getCamera();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        whitelist.collideWith(ray, results);

        return results;
    }
}
