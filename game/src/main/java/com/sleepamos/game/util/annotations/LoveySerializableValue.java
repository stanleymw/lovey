package com.sleepamos.game.util.annotations;

import java.lang.annotation.*;

/**
 * Sets a retained name to be kept in serialized files. This name can be kept consistent through name changes in code.
 * The value set via this annotation takes precedence over the declared variable name in the class.
 *
 * @apiNote If this annotation is not present, the name defined in the class file will be used. Changes to this annotated value
 * should correspond to a VERSION change.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoveySerializableValue {
    String value();
}
