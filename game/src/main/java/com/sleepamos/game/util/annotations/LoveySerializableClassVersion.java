package com.sleepamos.game.util.annotations;

import java.lang.annotation.*;

/**
 * Indicates the class version for a LoveySerializable object.
 * This value should be static.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoveySerializableClassVersion {
}
