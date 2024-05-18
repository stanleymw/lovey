package com.sleepamos.game.audio;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.sleepamos.game.Lovey;

/**
 * Holds all the audios
 */
public final class Audio {
    public static void initialize() {
    }

    public static AudioNode load(String fileName) {
        return new AudioNode(Lovey.getInstance().getAssetManager(), fileName, AudioData.DataType.Stream);
    }
}
