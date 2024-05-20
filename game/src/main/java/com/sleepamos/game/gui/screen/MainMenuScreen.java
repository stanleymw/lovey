package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.util.FileUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        window.addChild(this.button("Play").withVAlign(VAlignment.Center).toOtherScreen(() -> {
            try {
                return new LevelSelectScreen(LevelSelectScreen.parseData(FileUtil.getFromResources("/Maps/levels.txt")), (selected -> {

                }));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        window.addChild(this.button("Settings").withVAlign(VAlignment.Center).toOtherScreen(SettingsScreen::new));
        window.addChild(this.button("Credits").withVAlign(VAlignment.Center).toOtherScreen(CreditsScreen::new));
        window.addChild(this.button("Beatmap Editor").withVAlign(VAlignment.Center).toOtherScreen(BeatmapEditorScreen::new));
        window.addChild(this.button("Quit").withVAlign(VAlignment.Center).withCommand(source -> Lovey.getInstance().stop()));
    }
}
