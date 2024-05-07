package com.sleepamos.game.beatmap;

import com.jme3.audio.AudioNode;
import com.sleepamos.game.util.serializer.LoveySerializable;
import com.sleepamos.game.util.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.util.annotations.LoveySerializableValue;

@SuppressWarnings("serial")
public class Beatmap implements LoveySerializable {
    @LoveySerializableClassVersion
    public static final byte CURRENT_VERSION = 10;

    @LoveySerializableValue("version")
    private final int version;

    @LoveySerializableValue("name")
    private final String name;

    @LoveySerializableValue("musicAuthor")
    private final String musicAuthor;

    @LoveySerializableValue("beatmapAuthor")
    private final String beatmapAuthor;

    @LoveySerializableValue("music")
    private final AudioNode music;

    @LoveySerializableValue("spawner")
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
