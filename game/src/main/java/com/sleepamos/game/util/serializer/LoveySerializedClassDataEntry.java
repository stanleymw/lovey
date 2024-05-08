package com.sleepamos.game.util.serializer;

import java.io.Serializable;

public record LoveySerializedClassDataEntry(String serializedName, Object data) implements Serializable {
}
