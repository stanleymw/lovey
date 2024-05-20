package com.sleepamos.game.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.sleepamos.game.Lovey;

import java.util.Timer;

public class TrackedAudioNode extends AudioNode {
    Timer t = new Timer();
    private Callback callback;

    public TrackedAudioNode(AudioData a, AudioKey key) {
        super(a, key);

        t.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Lovey.getInstance().enqueue(() -> {
                    if (callback != null) {
                        callback.onUpdate();
                    }
                });
            }
        }, 0, 200);
    }

    public TrackedAudioNode(AssetManager assetManager, String name, AudioData.DataType type) {
        super(assetManager, name, type);

        t.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Lovey.getInstance().enqueue(() -> {
                    if (callback != null) {
                        callback.onUpdate();
                    }
                });
            }
        }, 0, 200);
    }

    public TrackedAudioNode() {
        super();

        t.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Lovey.getInstance().enqueue(() -> {
                    if (callback != null) {
                        callback.onUpdate();
                    }
                });
            }
        }, 0, 200);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @FunctionalInterface
    public interface Callback {
        void onUpdate();
    }
}
