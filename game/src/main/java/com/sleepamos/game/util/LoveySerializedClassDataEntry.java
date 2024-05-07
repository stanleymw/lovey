package com.sleepamos.game.util;

import java.io.Serializable;

record LoveySerializedClassDataEntry(String serializedName, Object data) implements Serializable {
}
