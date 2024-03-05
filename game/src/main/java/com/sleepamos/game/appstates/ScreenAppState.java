package com.sleepamos.game.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.GuiGlobals;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.gui.ScreenHandler;

public class ScreenAppState extends BaseAppState {
    private ScreenHandler screenHandler;

    @Override
    protected void initialize(Application app) {
        this.screenHandler = ((Lovey) app).getScreenHandler();
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        GuiGlobals.getInstance().setCursorEventsEnabled(true, true);
        if(!this.screenHandler.isShowingAScreen()) {
            this.screenHandler.hideLastShownScene();
        }
    }

    @Override
    protected void onDisable() {
        GuiGlobals.getInstance().setCursorEventsEnabled(false, true);
        if(this.screenHandler.isShowingAScreen()) {
            this.screenHandler.hideAllScreens();
        }
    }

    @Override
    public void update(float tpf) {
        // run every frame we're enabled
    }
}
