package com.sleepamos.game.util;

import java.io.Serializable;

public class LoveySerializedClass<T> implements Serializable {
    private final byte version;

    private final List<LoveySerializedClass<? super T>> superclasses;
    private final Map<String, Object> data;

    public LoveySerializedClass(byte version, List<String> names, List<Object> data) {

    }
}