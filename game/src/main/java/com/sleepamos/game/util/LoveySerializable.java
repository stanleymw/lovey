package com.sleepamos.game.util;

import java.io.*;

/**
 * A value serializable by the {@link LoveySerializer}. Extending classes should declare a VERSION value annotated with {@link com.sleepamos.game.util.annotations.LoveySerializableClassVersion LoveySerializableClassVersion}, otherwise the version defaults to 0.
 *
 * @apiNote This class extends {@link Serializable} so that {@link org.objenesis.instantiator.SerializationInstantiatorHelper#getNonSerializableSuperClass(Class) getNonSerializableSuperClass(Class)}
 * reads this class as a serializable class and will walk up the inheritance chain properly.
 * @see LoveySerializer
 */

public interface LoveySerializable extends Serializable {
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NonFatalException("Attempted to serialize class " + this.getClass().getSimpleName() + ", which extends LoveySerializable and should be serialized with the LoveySerializer.");
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new NonFatalException("Attempted to deserialize class " + this.getClass().getSimpleName() + ", which extends LoveySerializable and should be serialized with the LoveySerializer.");
    }
}
