package com.sleepamos.game.gui.screen;

import java.nio.file.Path;

public class CustomBeatmapSelectScreen extends FolderSelectorScreen {
    public CustomBeatmapSelectScreen(Path rootFolder, OnSelectionCallback callback) {
        super(rootFolder, callback);
    }

    public CustomBeatmapSelectScreen(OnSelectionCallback callback) {
        super(callback);
    }
}
