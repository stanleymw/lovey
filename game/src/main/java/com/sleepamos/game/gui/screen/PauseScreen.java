package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.sleepamos.game.Lovey;

public class PauseScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(this.getScreenWidth() / 3f, this.getScreenHeight(), 0);
        window.scale(1.75f);

        window.addChild(this.buttonWithCommand("Resume", source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove ourselves
            Lovey.getInstance().toggleScreenMode(false);
        }));
        window.addChild(this.buttonToOtherScreen("Settings", new SettingsScreen()));
        window.addChild(this.buttonWithCommand("Quit", source -> Lovey.getInstance().getScreenHandler().hideLastShownScreen()));
    }
}
