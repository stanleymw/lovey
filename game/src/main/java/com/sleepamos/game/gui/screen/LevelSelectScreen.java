package com.sleepamos.game.gui.screen;

import java.io.File;
import java.nio.file.Path;

public class LevelSelectScreen extends FolderSelectorScreen {
    public static String[] parseData(String data) {
        data = data.replace("\r", ""); // i hate windows (this bug took me a solid hour to figure out) (and the night before it was due)
        return data.split("\n");
    }

    private final String[] fileNames;

    public LevelSelectScreen(String rootFolder, String[] fileNames, OnSelectionCallback callback) {
        super(Path.of(rootFolder), callback);

        this.fileNames = fileNames;
    }

    @Override
    protected String[] dirNamesProvider() {
        return this.fileNames;
    }

    @Override
    protected boolean selectionRequirements(File f) {
        return f.toPath().resolve("beatmap.lovey").toFile().exists() && f.toPath().resolve("audio.wav").toFile().exists();
    }
}
