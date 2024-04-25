package com.sleepamos.game.beatmap;

import com.jme3.audio.AudioNode;
import com.sleepamos.game.util.LoveySerializable;

public class Beatmap implements LoveySerializable {
    public static final byte CURRENT_VERSION = 10;

    private final int version;
    private final String name;
    private final String musicAuthor;
    private final String beatmapAuthor;
    private final AudioNode music;
    private final InteractableSpawner spawner;

    public Beatmap(int version, String name, String musicAuthor, String beatmapAuthor, AudioNode music, InteractableSpawner spawner) {
        this.version = version;
        this.name = name;
        this.musicAuthor = musicAuthor;
        this.beatmapAuthor = beatmapAuthor;
        this.music = music;
        this.spawner = spawner;
    }
}
