package com.sleepamos.game.gui.screen;

import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.util.AssetsUtil;
import com.sleepamos.game.util.FileUtil;

import java.nio.file.Path;

public class FolderSelectorScreen extends EscapableScreen {
    private final Path rootFolder;
    private final OnSelectionCallback callback;

    private Button selected;

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
        Container beatmapListUI = this.createAndAttachContainer();
        beatmapListUI.setLocalTranslation(this.getScreenWidth() / 3.5f, this.getScreenHeight() - 20, 0);
        beatmapListUI.setPreferredSize(new Vector3f(this.getScreenWidth() / 4f, this.getScreenHeight() / 5f, 0));
        beatmapListUI.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        String[] dirNames = FileUtil.getDirectoryNames(this.rootFolder, (dirFile) -> dirFile.toPath().resolve("beatmap.lovey").toFile().exists()); // wonderful code
        for(String dirName : dirNames) {
            beatmapListUI.addChild(this.button(dirName).withHAlign(HAlignment.Left).withVAlign(VAlignment.Center).withTextureEnabled(false).withOffset(50, 0, 0).withCommand(source -> {
                source.setEnabled(false);
                if(this.selected != null) {
                    selected.setEnabled(true);
                }
                selected = source;
            }));
        }

        Container selectionUI = this.createAndAttachContainer();
        selectionUI.scale(0.5f);
        selectionUI.setLocalTranslation(this.getScreenWidth() / 2.2f, this.getScreenHeight() * (1 - (dirNames.length * 0.1f)), 0);

        selectionUI.addChild(this.button("<").square().withFontSize(15).withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {

        }));

        selectionUI.addChild(this.button(">").square().withFontSize(15).withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {

        }));

        selectionUI.addChild(this.button("Open").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {

        }));
    }

    @Override
    public void onEscape() {
        this.selected = null;
    }

    public Path getSelected() {
        if(this.selected != null) {
            return this.rootFolder.resolve(this.selected.getName());
        }
        return null;
    }

    @FunctionalInterface
    public interface OnSelectionCallback {
        void onSelect(Path selected);
    }
}
