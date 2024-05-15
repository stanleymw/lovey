package com.sleepamos.game.beatmap;

import com.sleepamos.game.interactables.Interactable;
import com.sleepamos.game.util.serializer.LoveySerializable;

public record Spawn(Interactable interactable, double time, double speed) implements LoveySerializable {
}
