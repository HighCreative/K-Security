package cloud.swiftnode.ksecurity.util;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class Reflections {

    public static Field getField(Class cls, String fieldName, boolean pub) throws NoSuchFieldException {
        if (pub) {
            return cls.getField(fieldName);
        }
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Object getFieldObj(Class cls, Object obj, String fieldName, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(cls, fieldName, pub);
        return field.get(obj);
    }

    public static Object getDecFieldObject(Class cls, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(cls, obj, fieldName, false);
    }

    public static Object getFieldObj(Class cls, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(cls, obj, fieldName, true);
    }

    public static void setField(Class cls, Object inst, String fieldName, Object val, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(cls, fieldName, pub);
        field.set(inst, val);
    }

    public static void setDecField(Class cls, Object inst, String fieldName, Object val) throws NoSuchFieldException, IllegalAccessException {
        setField(cls, inst, fieldName, val, false);
    }
}
