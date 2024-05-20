package com.sleepamos.game.gui.screen;

import java.io.File;
import java.nio.file.Path;

public class BeatmapCreationScreen extends FolderSelectorScreen {
    public BeatmapCreationScreen(Path rootFolder, OnSelectionCallback callback) {
        super(rootFolder, callback);
    }

    public BeatmapCreationScreen(OnSelectionCallback callback) {
        super(callback);
    }

    @Override
    protected boolean selectionRequirements(File f) {
        return f.toPath().resolve("audio.wav").toFile().exists() && !f.toPath().resolve("beatmap.lovey").toFile().exists();
    }
}
