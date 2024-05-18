package com.sleepamos.game.beatmap;

import com.sleepamos.game.serializer.LoveySerializable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;

@SuppressWarnings("serial")
public class Beatmap implements LoveySerializable {
    @LoveySerializableClassVersion
    public static final byte VERSION = 10;

    public String getName() {
        return name;
    }

    public String getMusicAuthor() {
        return musicAuthor;
    }

    public String getBeatmapAuthor() {
        return beatmapAuthor;
    }

    public long getLengthSec() {
        return lengthSec;
    }

    public InteractableSpawner getSpawner() {
        return spawner;
    }

    @LoveySerializableValue("name")
    private final String name;

    @LoveySerializableValue("musicAuthor")
    private final String musicAuthor;

    @LoveySerializableValue("beatmapAuthor")
    private final String beatmapAuthor;

    @LoveySerializableValue("lengthSec")
    private final long lengthSec;

    @LoveySerializableValue("spawner")
    private final InteractableSpawner spawner;

    public Beatmap(String name, String musicAuthor, String beatmapAuthor, long lengthSec, InteractableSpawner spawner) {
        this.name = name;
        this.musicAuthor = musicAuthor;
        this.beatmapAuthor = beatmapAuthor;
        this.lengthSec = lengthSec;
        this.spawner = spawner;
    }

    public Beatmap() {
        this("", "", "", 0, new InteractableSpawner());
    }
}
