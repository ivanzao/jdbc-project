package util;

import entity.annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class ReflectionUtils {

    public static Field getIdField(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return fields[0];
    }

    public static Method getGetterMethodForField(Field field, Class klass) throws NoSuchMethodException {
        String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return klass.getDeclaredMethod(methodName);
    }

    public static Method getMapperMethod(String className) throws NoSuchMethodException {
        String methodName = "get" + className + "FromResultSet";
        return Mapper.class.getDeclaredMethod(methodName, ResultSet.class);
    }

    public static boolean hasField(String field, Class klass) {
        try {
            return klass.getDeclaredField(field) != null;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }
}
