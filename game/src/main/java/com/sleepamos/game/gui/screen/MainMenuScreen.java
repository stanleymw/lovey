package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

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
