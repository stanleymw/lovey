package com.sleepamos.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.sleepamos.game.appstates.InGameAppState;
import com.sleepamos.game.appstates.ScreenAppState;
import com.sleepamos.game.gui.ScreenHandler;

import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class Lovey extends SimpleApplication {
    private static Lovey instance;

    /**
     * Get the instance of the game.
     * You should wait until after {@link #simpleInitApp()} has been called otherwise this will be null.
     * @return The game instance.
     */
    public static Lovey getInstance() {
        return instance;
    }

    private ScreenHandler screenHandler;

    private final ActionListener actionListener = (String name, boolean keyPressed, float tpf) -> {
        // false = key released!
        switch(name) {
            case "Pause" -> System.out.println("paused");
        }
    };

    public Lovey(AppState... initialStates) {
        super(initialStates);
        instance = this;
    }

    @Override
    public void simpleInitApp() {
        ScreenHandler.initialize(this);
        this.screenHandler = ScreenHandler.getInstance();
        this.getRootNode().attachChild(this.getGuiNode());

        this.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY); // delete the defaults,
        this.configureMappings(this.getInputManager()); // then add our own
    }

    private String[] hijackMappingsList(InputManager mgrInstance) {
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
            return new String[]{};
        }
    }

    private void configureMappings(InputManager mgr) {
        mgr.addMapping("Pause", new KeyTrigger(KeyInput.KEY_ESCAPE));

        mgr.addListener(this.actionListener, this.hijackMappingsList(mgr));
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    /**
     * Get the screen handler.
     * You should wait until after {@link #simpleInitApp()} has been called otherwise this will be null.
     * @return The screen handler for the game.
     */
    public ScreenHandler getScreenHandler() {
        return this.screenHandler;
    }

    public void toggleScreenMode(boolean screensEnabled) {
        this.getStateManager().getState(ScreenAppState.class).setEnabled(screensEnabled);
        this.getStateManager().getState(InGameAppState.class).setEnabled(!screensEnabled);
    }
}
