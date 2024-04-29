package com.sleepamos.game.util;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom serialization interface that is able to handle file versioning smoothly.
 * The serializer first creates a set of class definitions with the layouts of each class being serialized, then writes serialized data
 * following the defined formats.
 * Arrays and objects extending {@link List} will have only their contents serialized.
 * <p>
 * Ex:
 * Given the following class A to be serialized:
 * {@snippet lang = "java":
 * public class A implements LoveySerializable {
 *     @LoveySerializableClassVersion
 *     public static final byte VERSION = 10;
 *
 *     private double d;
 *     private double e;
 *     private boolean bool;
 *     private B b; // B implements LoveySerializable
 *     private transient C c; // C does not implement LoveySerializable
 *     private transient B anotherB; // B implements LoveySerializable
 *     private C c; // this would issue a warning on serialize if uncommented since C does not implement LoveySerializable. C would not be included in the final serialization.
 *     private List<B> bList; // Only the contents of the list will be serialized.
 *     @LoveySerializableValue("arrOfB")
 *     private B[] bArr;
 *     // Class continues...
 * }
 *
 * public class B implements LoveySerializable {
 *     private double bSubVar;
 * }
 * public class C {}
 *
 * public class D extends A {
 *     private long dValue;
 * }
 * }
 * The format of a serialized file for A would be similar to this format, without line breaks or information in parentheses:
 * <pre>
 * type A: (A must be of the root type being serialized, any following class structure definitions after A are for included sub-elements)
 *     version VERSION (the value, stored as a byte) (note: this value will NOT be read from the class; instead it must be passed in to the serializer)
 *     double "d";
 *     double "e";
 *     boolean "bool";
 *     val B "b"; (val indicates an {@link Object} that will need to be defined)
 *     val List type=B len=bList.size() "bList"
 *     val Array type=B len=bArr.length "arrOfB"
 * fin;
 * type B:
 *     version VERSION (the value)
 *     double "bSubVar";
 * fin;
 * val root:
 *     d
 *     e
 *     bool
 *     subval b:
 *         bSubVar
 *     fin;
 *     subval bList:
 *         bList.get(0)
 *         ... until all elements of bList
 *     fin;
 *     subval bArr:
 *         bArr[0]
 *         ... until all elements of bArr
 *     fin;
 * fin;
 * end file;
 * </pre>
 *
 * The format of a serialized file for D would be similar to the following:
 * <pre>
 * type D:
 *     version VERSION
 *     super A;
 *     long "dValue";
 * fin;
 * type A: (A must be of the root type being serialized, any following class structure definitions after A are for included sub-elements)
 *     version VERSION (the value, stored as a byte) (note: this value will NOT be read from the class; instead it must be passed in to the serializer)
 *     double "d";
 *     double "e";
 *     boolean "bool";
 *     val B "b"; (val indicates an {@link Object} that will need to be defined)
 *     val List type=B len=bList.size() "bList"
 *     val Array type=B len=bArr.length "arrOfB"
 * fin;
 * type B:
 *     version VERSION (the value)
 *     double "bSubVar";
 * fin;
 * val root:
 *     super A:
 *         d
 *         e
 *         bool
 *         subval b:
 *             bSubVar
 *         fin;
 *         subval bList:
 *             bList.get(0)
 *             ... until all elements of bList
 *         fin;
 *         subval bArr:
 *             bArr[0]
 *             ... until all elements of bArr
 *         fin;
 *     dValue
 * fin;
 * end file;
 * </pre>
 */
public class LoveySerializer {
    private static Objenesis objenesis = new ObjenesisSerializer();

    public static String serialize(LoveySerializable s) {
        return serialize(s, (byte) 0);
    }

    public static String serialize(LoveySerializable s, byte version) {
        String serialized = String.valueOf(version);

        return serialized;
    }

    public static LoveySerializable deserialize(String s) {
        return deserialize(s, (byte) 0);
    }

    public static LoveySerializable deserialize(String s, byte expectedVersion) {
        return deserialize(s, expectedVersion, (serialized, fileVersion, eVersion) -> {
            throw new NonFatalException("Unexpected version mismatch, file: " + fileVersion + ", class: " + eVersion);
        });
    }

    public static LoveySerializable deserialize(String s, byte expectedVersion, VersionMismatchedDeserializer onVersionMismatch) {
        byte version = s.getBytes()[0];
        if(version != expectedVersion) {
            return onVersionMismatch.deserialize(s, version, expectedVersion);
        }

        return null;
    }

    /**
     * Handles the conversion from a LoveySerializable serialized in a different (prior) version to the latest version.
     */
    @FunctionalInterface
    public interface VersionMismatchedDeserializer {
        LoveySerializable deserialize(String serialized, byte fileVersion, byte expectedVersion);
    }

    /**
     * Serializes class definitions. The class will be serialized following the format defined in the javadoc for {@link LoveySerializer}.
     * The returned value may be immediately placed into the file.
     *
     * @apiNote The class will be serialized bottom-up, with the serialized class's definition being the first and moving up through the inheritance tree.
     * @see LoveySerializer
     * @param clazz The class to create a class definition for.
     */
    private static String serializeClassHierarchy(Class<? extends LoveySerializable> clazz) {
        // Follow JDK's strategy of serializing everything that is serializable since objenesis can handle calling the constructor of the 1st non-serializable class for us.
        ArrayList<Class<?>> superclasses = new ArrayList<>();
        Class<?> superclass = clazz.getSuperclass();
        while(superclass != null && LoveySerializable.class.isAssignableFrom(superclass)) {
            superclasses.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return "";
    }

    /**
     * Represents a field definition within a class definition.
     * 
     * @param typeName The field's object type. Indicate "List" for a List and "Array" for arrays.
     * @param fieldName The name to serialize.
     * @param ofType If the field is of a certain type, the type.
     * @param len If the field is a List or array, the length.
     */
    private record SerializedFieldDefinition(String typeName, String fieldName, String ofType, int len) {
        public SerializedFieldDefinition(String typeName, String fieldName, String ofType) {
            this(typeName, fieldName, ofType, -1);
        }
        public SerializedFieldDefinition(String typeName, String fieldName) {
            this(typeName, fieldName, "", -1);
        }

        public String serialize() {
            if(typeNameIsPrimitive(typeName)) {
                return typeName + " \"" + fieldName + "\";";
            }

            String serialized = "val ";
            if(typeNameIsListOrArray(typeName)) {
                if(ofType == null || ofType.equals("")) {
                    throw new NonFatalException("Attempt to serialize " + typeName + " " + fieldName + " resulted in error due to undefined ofType");
                }
                serialized += typeName + " type=" + ofType + " len=" + len + " \"" + fieldName + "\";";
            }
        }
    }

    private static String serializeSingleClassType(Class<? extends LoveySerializable> clazz) {
        ArrayList<String> fieldNames = new ArrayList<>();
        // iterate over all fields.
        // if a field is transient or static, skip
        // if a field is not transient/static, check that the field is also LoveySerializable OR is primitive OR is an array/List of one of the first two requirements. If not, throw a NonFatalError
        // if the field meets all requirements, add the field name (first checking for an @LoveySerializableValue, otherwise using the declared name)
        for(Field field : clazz.getDeclaredFields()) {
            if(!Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                if(typeIsLoveySerializable(field.getType())) {
                    LoveySerializableValue annotation = field.getAnnotation(LoveySerializableValue.class);
                    if(annotation != null) {
                        fieldNames.add(annotation.value());
                    } else {
                        fieldNames.add(field.getName());
                    }
                } else {
                    System.out.println("Unexpected field type " + field.getType().getSimpleName() + " in class " + clazz.getSimpleName() + " does not impl LoveySerializable but was not marked as transient for serializer");
                }
            }
        }

        return "";
    }

    private static boolean typeIsLoveySerializable(Class<?> clazz) {
        if(LoveySerializable.class.isAssignableFrom(clazz) || clazz.isPrimitive()) { // is LoveySerializable or primitive
            return true;
        }

        if(List.class.isAssignableFrom(clazz) && typeIsLoveySerializable((Class<?>)((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[0])) { // is list of a LoveySerializable component or primitive (recursive OK)
            return true;
        }

        //noinspection RedundantIfStatement
        if(clazz.isArray() && typeIsLoveySerializable(clazz.getComponentType())) { // is array of a LoveySerializable component or primitive (recursive OK)
            return true;
        }

        return false;
    }

    private static boolean typeNameIsPrimitive(String typeName) {
        return typeName.equals("boolean") ||
            typeName.equals("character") ||
            typeName.equals("byte") ||
            typeName.equals("short") ||
            typeName.equals("int") ||
            typeName.equals("long") ||
            typeName.equals("float") ||
            typeName.equals("double");
    }

    private static boolean typeNameIsListOrArray(String typeName) {
        return typeName.equals("List") || typeName.equals("Array");
    }
}
