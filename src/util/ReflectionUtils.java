package util;

import entity.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class ReflectionUtils {

    public static String getIdName(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return fields[0].getName();
    }

    public static Method getGetterMethodForField(Field field, Class klass) {
        String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        try {
            return klass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMapperMethod(String className) {
        String methodName = "get" + className + "FromResultSet";
        try {
            return Mapper.class.getDeclaredMethod(methodName, ResultSet.class);
        } catch (NoSuchMethodException e) {
            System.out.println("No such method " + methodName + " on class 'Mapper'");
            e.printStackTrace();
        }
        return null;
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
