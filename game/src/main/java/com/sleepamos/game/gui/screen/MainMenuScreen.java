package com.sleepamos.game.gui.screen;

import com.jme3.app.state.AppStateManager;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.appstates.InGameAppState;
import com.sleepamos.game.appstates.ScreenAppState;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(300, 300, 0);

        // Add some elements
        window.addChild(new Label("Hello, World."));
        Button clickMe = window.addChild(new Button("Click Me"));
        clickMe.addClickCommands(source -> {
            AppStateManager mgr = Lovey.getInstance().getStateManager();
            mgr.getState(ScreenAppState.class).setEnabled(false);
            mgr.getState(InGameAppState.class).setEnabled(true);
        });
    }
}
