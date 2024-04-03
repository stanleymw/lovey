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
        window.setLocalTranslation(30, this.getScreenHeight(), 0);
        window.scale(1.75f);

        // Add some elements
        // window.addChild((Node)((new Label("The Adventures of\nLovey the Penguin"))));

        window.addChild(new Button("Enter Game")).addClickCommands(source -> Lovey.getInstance().toggleScreenMode(false));
        window.addChild(this.createButtonToOtherScreen("Settings", new SettingsScreen()));
        window.addChild(this.createButtonToOtherScreen("Credits", new CreditsScreen()));
        window.addChild(new Button("Quit")).addClickCommands(source -> Lovey.getInstance().stop());
    }
}
