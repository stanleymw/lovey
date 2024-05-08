package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
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

        window.addChild(this.buttonWithCommand(this.buttonWithAlign("Enter Game", VAlignment.Center), source -> {
            Lovey.getInstance().launchMap();
            Lovey.getInstance().getScreenHandler().showScreen(new NoScreen());
            Lovey.getInstance().useGUIBehavior(false);
        }));
        window.addChild(this.buttonToOtherScreen(this.buttonWithAlign("Settings", VAlignment.Center), new SettingsScreen()));
        window.addChild(this.buttonToOtherScreen(this.buttonWithAlign("Credits", VAlignment.Center), new CreditsScreen()));
        window.addChild(this.buttonToOtherScreen(this.buttonWithAlign("Beatmap Editor", VAlignment.Center), new BeatmapEditorScreen()));
        window.addChild(this.buttonWithCommand(this.buttonWithAlign("Quit", VAlignment.Center), source -> Lovey.getInstance().stop()));
    }
}
