package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.sleepamos.game.Lovey;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(300, 300, 0);
        window.scale(1.75f);

        // Add some elements
        window.addChild((Node)((new Label("The Adventures of\nLovey the Penguin")).scale(0.75f)));
        Button clickMe = window.addChild(new Button("Enter Game"));
        clickMe.addClickCommands(source -> Lovey.getInstance().toggleScreenMode(false));
    }
}
