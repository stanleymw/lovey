package com.sleepamos.game.util;

import java.io.Serializable;

public record LoveySerializedClassDataEntry(String serializedName, Object data) implements Serializable {
}
