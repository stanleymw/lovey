package com.sleepamos.game.audio;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.sleepamos.game.Lovey;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

/**
 * Holds all the audios
 */
public final class Audio {
    public static void initialize() {
    }

    @SneakyThrows
    public static AudioNode load(String fileName) {
        return load(Path.of(fileName));
    }

    @SneakyThrows
    public static AudioNode load(Path path) {
        AudioKey key = new AudioKey(path.getFileName().toString());
        AudioData a = Lovey.getInstance().getAssetManager().loadAssetFromStream(key, new FileInputStream(path.toFile()));
        AudioNode audioNode = new AudioNode(a, key); // im such a cool programmer this is so cool guys !!!
        audioNode.setPositional(false);

        return audioNode;
    }
}
