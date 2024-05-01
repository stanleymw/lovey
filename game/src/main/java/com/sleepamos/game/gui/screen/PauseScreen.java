package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;

public class PauseScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(this.getScreenWidth() / 3f, this.getScreenHeight(), 0);
        window.scale(6);

        window.addChild(this.buttonWithCommand(this.buttonWithAlign("Resume", HAlignment.Center, VAlignment.Center), source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove ourselves
            Lovey.getInstance().toggleScreenMode(false);
        }));

        window.addChild(this.buttonToOtherScreen(this.buttonWithAlign("Settings", HAlignment.Center, VAlignment.Center), new SettingsScreen()));
        window.addChild(this.buttonWithCommand(this.buttonWithAlign("Quit", HAlignment.Center, VAlignment.Center), source -> Lovey.getInstance().getScreenHandler().hideLastShownScreen()));
    }
}
