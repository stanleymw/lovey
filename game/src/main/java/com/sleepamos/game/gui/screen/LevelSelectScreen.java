package com.sleepamos.game.gui.screen;

import com.sleepamos.game.util.FileUtil;

import java.io.File;
import java.nio.file.Path;

public class LevelSelectScreen extends FolderSelectorScreen {
    public static String[] parseData(String data) {
        return data.split("\n");
    }

    private final String[] fileNames;

    public LevelSelectScreen(String[] fileNames, OnSelectionCallback callback) {
        super(callback);

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
