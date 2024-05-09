package com.sleepamos.game;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;
import com.sleepamos.game.appstates.InGameAppState;
import com.sleepamos.game.appstates.ScreenAppState;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.audio.Audio;
import com.sleepamos.game.gui.ScreenHandler;
import com.sleepamos.game.gui.screen.PauseScreen;
import com.sleepamos.game.util.NonFatalException;

import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link BaseAppState}, Custom controls {@link AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class Lovey extends SimpleApplication {
    private static Lovey instance;
    private boolean inGame;

    /**
     * Get the instance of the game.
     * You should wait until after {@link #simpleInitApp()} has been called otherwise this will be null.
     * @return The game instance.
     */
    public static Lovey getInstance() {
        return instance;
    }

    private ScreenHandler screenHandler;

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    private final ActionListener actionListener = (String name, boolean keyPressed, float tpf) -> {
        // false = key released! we only want to do stuff on key release for these ones.
        if(!keyPressed) {
            switch(name) {
                case "Escape" -> {
                    InGameAppState inGame = this.getStateManager().getState(InGameAppState.class);
                    if(inGame != null && inGame.isEnabled()) {
                        this.useGUIBehavior(true);
                        this.pauseGame(true);
                        this.getScreenHandler().showScreen(new PauseScreen());
                    } else if(this.getStateManager().getState(ScreenAppState.class).isEnabled()) {
                        this.getScreenHandler().onEscape();
                    }
                }
            }
        }
    };

    public Lovey(AppState... initialStates) {
        super(initialStates);
        instance = this;
        inGame = false;
    }

    public Lovey() {
        this(new ScreenAppState(), new StatsAppState(), new AudioListenerState(), new FlyCamAppState());
    }

    @Override
    public void simpleInitApp() {
        ScreenHandler.initialize(this);

        this.screenHandler = ScreenHandler.getInstance();
        this.getRootNode().attachChild(this.getGuiNode()); // make sure it's attached

        this.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT); // delete the default
        this.getFlyByCamera().setMoveSpeed(0);
        this.getCamera().setFrame(new Vector3f(0, 3, 0), new Quaternion());

        this.configureMappings(this.getInputManager()); // then add our own
        Assets.initialize();
        Audio.initialize();

        this.getStateManager().getState(StatsAppState.class).getFpsText().setSize(9);

    }

    @SuppressWarnings("unchecked")
    private static String[] hijackMappingsList(InputManager mgrInstance) {
        try {
            // InputManager hides a lot of stuff from us here - including the Mapping static inner class, which is why I've left it as ?
            // We access the field through the given InputManager instance, cast it to a HashMap with key type String, then convert the key set
            // to a String[] that we can use.

            Field mappingField = InputManager.class.getDeclaredField("mappings");
            mappingField.setAccessible(true); // Since the field is normally "private final", make it accessible.

            // Ignore the big yellow line here, I think I know what I'm doing
            return ((HashMap<String, ?>)(mappingField.get(mgrInstance))).keySet().toArray(new String[0]); // trust me bro
        } catch(Exception e) {
            System.out.println("Unable to get the mappings list");
            e.printStackTrace();
            throw new AssertionError("Closing game due to error in startup.");
        }
    }

    private void configureMappings(InputManager mgr) {
        mgr.addMapping("Escape", new KeyTrigger(KeyInput.KEY_ESCAPE));

        mgr.addListener(this.actionListener, hijackMappingsList(mgr)); // add all our defined mappings to the action listener
    }

    @Override
    public void update() {
        try {
            super.update();
        } catch(NonFatalException e) {
            System.out.println("A non-fatal exception was thrown");
            e.printStackTrace();
            // TODO: Write error handling code (and make sure this even works)
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
    }

    @Override
    protected BitmapFont loadGuiFont() {
        return super.loadGuiFont();
        // return Assets.FONT;
    }

    /**
     * Get the screen handler.
     * You should wait until after {@link #simpleInitApp()} has been called otherwise this will be null.
     * @return The screen handler for the game.
     */
    public ScreenHandler getScreenHandler() {
        return this.screenHandler;
    }

    /**
     * Set to true whenever we are in a GUI. (uses mouse behavior/stuff when screen opened)
     * @param screensEnabled
     */
    public void useGUIBehavior(boolean screensEnabled) {
        this.getStateManager().getState(ScreenAppState.class).setEnabled(screensEnabled);
    }

    public void pauseGame(boolean paused) {
        this.getStateManager().getState(InGameAppState.class).setEnabled(!paused);
    }

    public void launchMap() {
//        this.getStateManager().getState(ScreenAppState.class).setEnabled(false);
        this.getStateManager().attach(new InGameAppState());
    }

    public void exitMap() {
        this.getStateManager().detach(this.getStateManager().getState(InGameAppState.class));
    }

    public boolean isInGame() {
        return inGame;
    }

    public AppSettings getSettings() {
        return this.settings;
    }
}
