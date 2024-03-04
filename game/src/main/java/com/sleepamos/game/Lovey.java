package com.sleepamos.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.sleepamos.game.gui.ScreenHandler;


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

    public Lovey(AppState... initialStates) {
        super(initialStates);
        instance = this;
    }

    @Override
    public void simpleInitApp() {
        ScreenHandler.initialize(this);
        this.screenHandler = ScreenHandler.getInstance();
        this.getRootNode().attachChild(this.getGuiNode());
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
}
