package com.sleepamos.game.gui.screen;

import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.util.AssetsUtil;
import com.sleepamos.game.util.FileUtil;

import java.nio.file.Path;

public class FolderSelectorScreen extends EscapableScreen {
    private final Path rootFolder;
    private final OnSelectionCallback callback;

    private String selected;

    public FolderSelectorScreen(Path rootFolder, OnSelectionCallback callback) {
        this.rootFolder = rootFolder;
        this.callback = callback;
        this.selected = null;
    }

    public FolderSelectorScreen(OnSelectionCallback callback) {
        this(FileUtil.getWorkingDirectory(), callback);
    }

    @Override
    protected void initialize() {
        Container container = this.createAndAttachContainer();
        container.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        System.out.println("rootfolder: " + rootFolder);
        String[] dirNames = FileUtil.getDirectoryNames(this.rootFolder, (dirFile) -> dirFile.toPath().resolve("beatmap.lovey").toFile().exists()); // wonderful code
        for(String dirName : dirNames) {
            container.addChild(this.buttonWithCommand(this.buttonWithAlign(dirName, HAlignment.Left, VAlignment.Center), source -> {
                source.setColor(new ColorRGBA(0, 0, 0, 100));
                selected = dirName;
            }));
        }
    }

    @Override
    protected void onEscape() {
        this.selected = null;
    }

    public Path getSelected() {
        return this.rootFolder.resolve(this.selected);
    }

    @FunctionalInterface
    public interface OnSelectionCallback {
        void onSelect(Path selected);
    }
}
