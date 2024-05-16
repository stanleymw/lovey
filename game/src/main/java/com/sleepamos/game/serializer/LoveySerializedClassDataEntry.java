package com.sleepamos.game.serializer;

import java.io.Serializable;

public record LoveySerializedClassDataEntry(String serializedName, Object data) implements Serializable {
}
