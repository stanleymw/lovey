package com.sleepamos.game.serializer;

import com.sleepamos.game.serializer.annotations.LoveySerializableClassVersion;

import java.io.*;

/**
 * A value serializable by the {@link LoveySerializer}. Extending classes should declare a VERSION value annotated with {@link LoveySerializableClassVersion LoveySerializableClassVersion}, otherwise the version defaults to 0.
 *
 * @apiNote This class extends {@link Serializable} so that {@link org.objenesis.instantiator.SerializationInstantiatorHelper#getNonSerializableSuperClass(Class) getNonSerializableSuperClass(Class)}
 * reads this class as a serializable class and will walk up the inheritance chain properly. This class should NOT be directly serialized; an error will be thrown.
 * @see LoveySerializer
 */

public interface LoveySerializable extends Serializable {
}
