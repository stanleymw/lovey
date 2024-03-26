package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.sleepamos.game.Lovey;

public class PauseScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();
        // window.setBackground(new QuadBackgroundComponent(Lovey.getInstance().getAssetManager().loadTexture("bg.png")));

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(300, 300, 0);
        window.scale(1.75f);

        window.addChild(new Button("Resume")).addClickCommands(source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove ourselves
            Lovey.getInstance().toggleScreenMode(false);
        });
        window.addChild(new Button("Return to Main Menu")).addClickCommands(source -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen();
        });
    }
}
