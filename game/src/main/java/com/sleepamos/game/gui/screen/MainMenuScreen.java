package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.audio.Audio;
import com.sleepamos.game.audio.TrackedAudioNode;
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.serializer.LoveySerializedClass;
import com.sleepamos.game.serializer.LoveySerializer;
import com.sleepamos.game.util.FileUtil;

import java.io.InputStream;

public class MainMenuScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        window.addChild(this.button("Play").withVAlign(VAlignment.Center).toOtherScreen(() -> {
            try {
                return new LevelSelectScreen(Assets.INHOUSE_BEATMAPS_RESOURCE_PATH, LevelSelectScreen.parseData(FileUtil.getFromResources(Assets.INHOUSE_BEATMAPS_RESOURCE_PATH + "/levels.txt")), (selected -> {
                    InputStream is = FileUtil.getInputStreamFromResources(selected.resolve("beatmap.lovey").toString().replace("\\", "/"));
                    Beatmap beatmap = LoveySerializer.deserialize(FileUtil.readSerializedObjectFromInputStream(is, LoveySerializedClass.class), Beatmap.class, (serialized, fileVersion, eVersion, c, obj) -> {
                        throw new NonFatalException("Unexpected version mismatch, stored file version: " + fileVersion + ", class defined version: " + eVersion);
                    });

//                    System.out.println("SENTIR");
                    System.out.println(beatmap.getSpawner().getTargetsToSpawn().get(0));

                    TrackedAudioNode audio = Audio.load(FileUtil.getInputStreamFromResources(selected.resolve("audio.wav").toString().replace("\\", "/")));

//                    Lovey.getInstance().launchMap(beatmap);
                    Lovey.getInstance().launchMap(Lovey.getInstance().getDemoMap());
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
