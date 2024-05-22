package com.sleepamos.game.gui.screen;

import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.util.AssetsUtil;
import com.sleepamos.game.util.FileUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
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
        Path wd = FileUtil.getWorkingDirectory();
        System.out.println(wd);
    }

    protected boolean selectionRequirements(File f) {
        return f.toPath().resolve("beatmap.lovey").toFile().exists();
    }

    protected String[] dirNamesProvider() {
        return FileUtil.getDirectoryNames(this.rootFolder, this::selectionRequirements); // wonderful code
    }

    @Override
    protected void initialize() {
        Container beatmapListUI = this.createAndAttachContainer();
        beatmapListUI.setLocalTranslation(this.getScreenWidth() / 3.5f, this.getScreenHeight() - 20, 0);
        beatmapListUI.setPreferredSize(new Vector3f(this.getScreenWidth() / 4f, this.getScreenHeight() / 5f, 0));
        beatmapListUI.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        String[] dirNames = this.dirNamesProvider();

        Button openButton = this.button("Open").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center)
                .withCommand(source -> this.callback.onSelect(this.getSelected()));

        openButton.setEnabled(false);

        for (String dirName : dirNames) {
            beatmapListUI.addChild(
                    this.button(dirName).withFontSize(20).withHAlign(HAlignment.Left).withVAlign(VAlignment.Center)
                            .withTextureEnabled(false).withOffset(50, 0, 0).withCommand(source -> {
                                source.setEnabled(false);
                                if (this.selected != null) {
                                    selected.setEnabled(true);
                                }
                                selected = source;
                                openButton.setEnabled(true);
                            }));
        }

        final float y = this.getScreenHeight() * (0.9f - (dirNames.length * 0.1f));

        Container leftButton = this.createAndAttachContainer();
        leftButton.setLocalTranslation(this.getScreenWidth() / 2.2f, y, 0);

        leftButton.addChild(this.button("<").withFontSize(15).square().withHAlign(HAlignment.Center)
                .withVAlign(VAlignment.Center).withCommand(source -> {

                }));

        Container rightButton = this.createAndAttachContainer();
        rightButton.setLocalTranslation(this.getScreenWidth() / 2.2f + 150, y, 0);

        rightButton.addChild(this.button(">").withFontSize(15).square().withHAlign(HAlignment.Center)
                .withVAlign(VAlignment.Center).withCommand(source -> {

                }));

        rightButton.addChild(openButton);
    }

    @Override
    public void onEscape() {
        this.selected = null;
    }

    @Nullable
    public Path getSelected() {
        if (this.selected != null) {
            return this.rootFolder.resolve(this.selected.getText());
        }
        return null;
    }

    @FunctionalInterface
    public interface OnSelectionCallback {
        void onSelect(Path selected);
    }
}
