package com.sleepamos.game.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.GuiGlobals;

public class ScreenAppState extends BaseAppState {
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
