package com.sleepamos.game.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.GuiGlobals;
import com.sleepamos.game.gui.screen.Screen;

public class ScreenAppState extends BaseAppState {
    private Screen currentScreen;

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        GuiGlobals.getInstance().requestCursorEnabled(this);
    }

    @Override
    protected void onDisable() {
        GuiGlobals.getInstance().releaseCursorEnabled(this);
    }

    @Override
    public void update(float tpf) {
        // run every frame we're enabled
    }
}
