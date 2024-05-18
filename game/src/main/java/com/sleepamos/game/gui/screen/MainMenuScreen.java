package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        window.addChild(this.button("Enter Game").withVAlign(VAlignment.Center).withCommand(source -> {
            Lovey.getInstance().launchMap();
            Lovey.getInstance().getScreenHandler().showScreen(new NoScreen());
            Lovey.getInstance().useGUIBehavior(false);
        }));
        window.addChild(this.button("Settings").withVAlign(VAlignment.Center).toOtherScreen(new SettingsScreen()));
        window.addChild(this.button("Credits").withVAlign(VAlignment.Center).toOtherScreen(new CreditsScreen()));
        window.addChild(this.button("Beatmap Editor").withVAlign(VAlignment.Center).toOtherScreen(new BeatmapEditorScreen()));
        window.addChild(this.button("Quit").withVAlign(VAlignment.Center).withCommand(source -> Lovey.getInstance().stop()));
    }
}
