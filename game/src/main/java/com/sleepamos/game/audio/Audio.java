package com.sleepamos.game.audio;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.sleepamos.game.Lovey;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Holds all the audios
 */
public final class Audio {
    public static void initialize() {
    }

    @SneakyThrows
    public static TrackedAudioNode load(String fileName) {
        return load(Path.of(fileName));
    }

    @SneakyThrows
    public static TrackedAudioNode load(Path path) {
        AudioKey key = new AudioKey(path.getFileName().toString());
        AudioData a = Lovey.getInstance().getAssetManager().loadAssetFromStream(key, new FileInputStream(path.toFile()));
        TrackedAudioNode audioNode = new TrackedAudioNode(a, key); // im such a cool programmer this is so cool guys !!!
        audioNode.setPositional(false);

        return audioNode;
    }

    @SneakyThrows
    public static TrackedAudioNode load(InputStream stream) {
        AudioKey key = new AudioKey("audio.wav");
        AudioData a = Lovey.getInstance().getAssetManager().loadAssetFromStream(key, stream);
        TrackedAudioNode audioNode = new TrackedAudioNode(a, key); // im such a cool programmer this is so cool guys !!!
        audioNode.setPositional(false);

        return audioNode;
    }
}
