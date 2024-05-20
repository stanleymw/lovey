package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.beatmap.Beatmap;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        System.out.println("INITIALIZING MAIN MENUUU");

        Container window = this.createAndAttachContainer();

        window.addChild(this.button("Enter Game").withVAlign(VAlignment.Center).withCommand(source -> {
            Lovey.getInstance().launchMap(Lovey.getInstance().getDemoMap());
        }));

        window.addChild(this.button("Settings").withVAlign(VAlignment.Center).toOtherScreen(SettingsScreen::new));
        window.addChild(this.button("Credits").withVAlign(VAlignment.Center).toOtherScreen(CreditsScreen::new));
        window.addChild(this.button("Beatmap Editor").withVAlign(VAlignment.Center).toOtherScreen(BeatmapEditorScreen::new));
        window.addChild(this.button("Quit").withVAlign(VAlignment.Center).withCommand(source -> Lovey.getInstance().stop()));
    }
}
