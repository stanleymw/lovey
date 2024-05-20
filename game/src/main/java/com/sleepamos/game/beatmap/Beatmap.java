package com.sleepamos.game.beatmap;

import com.sleepamos.game.serializer.LoveySerializable;
import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;
import com.sleepamos.game.serializer.annotations.LoveySerializableValue;

import java.util.ArrayList;

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

    public InteractableSpawner getSpawner() {
        return spawner;
    }

    @LoveySerializableValue("name")
    private final String name;

    @LoveySerializableValue("musicAuthor")
    private final String musicAuthor;

    @LoveySerializableValue("beatmapAuthor")
    private final String beatmapAuthor;

    @LoveySerializableValue("spawner")
    private final InteractableSpawner spawner;

    public Beatmap(String name, String musicAuthor, String beatmapAuthor, InteractableSpawner spawner) {
        this.name = name;
        this.musicAuthor = musicAuthor;
        this.beatmapAuthor = beatmapAuthor;
        this.spawner = spawner;
    }

    public Beatmap() {
        this("", "", "", new InteractableSpawner());
    }
}
