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
        window.scale(3);

        window.addChild(this.button("Resume").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove ourselves

            Lovey.getInstance().useGUIBehavior(false);
            Lovey.getInstance().pauseGame(false);
        }));

        window.addChild(this.button("Settings").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(SettingsScreen::new));
        window.addChild(this.button("Quit").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove ourselves
            Lovey.getInstance().exitMap();

            Lovey.getInstance().useGUIBehavior(true);
        }));
    }
}
