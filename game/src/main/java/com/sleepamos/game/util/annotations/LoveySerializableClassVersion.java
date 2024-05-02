package com.sleepamos.game.util.annotations;

import java.lang.annotation.*;

/**
 * Indicates the class version for a LoveySerializable object.
 * This value should be static. Only one variable should be annotated with this in a single class definition (subclasses may define their own value). Multiple annotations will result in undefined behavior.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoveySerializableClassVersion {
}
