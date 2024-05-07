package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
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

        window.addChild(this.buttonWithCommand("Enter Game", source -> {
            Lovey.getInstance().launchMap();
            Lovey.getInstance().getScreenHandler().showScreen(new NoScreen());
            Lovey.getInstance().useGUIBehavior(false);
        }));
        window.addChild(this.buttonToOtherScreen("Settings", new SettingsScreen()));
        window.addChild(this.buttonToOtherScreen("Credits", new CreditsScreen()));
        window.addChild(this.buttonToOtherScreen("Beatmap Editor", new BeatmapEditorScreen()));
        window.addChild(this.buttonWithCommand("Quit", source -> Lovey.getInstance().stop()));
    }
}
