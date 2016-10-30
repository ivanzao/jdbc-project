package util;

import entity.annotation.ManyToOne;

import java.lang.reflect.Field;

public class DatabaseNaming {

    public static String getEntityName(String name) {
        StringBuilder builder = new StringBuilder();
        char[] nameAsArray = name.toCharArray();
        for (char character : nameAsArray) {
            if (!Character.isUpperCase(character)) {
                builder.append(character);
            } else if (nameAsArray[0] != character) {
                builder.append("_");
                builder.append(Character.toLowerCase(character));
            } else {
                builder.append(Character.toLowerCase(character));
            }
        }
        return builder.toString();
    }

    public static String getColumnName(Field field) {
        StringBuilder builder = new StringBuilder();
        String fieldName = field.getName();
        char[] nameAsArray = fieldName.toCharArray();
        for (char character : nameAsArray) {
            if (!Character.isUpperCase(character)) {
                builder.append(character);
            } else if (nameAsArray[0] != character) {
                builder.append("_");
                builder.append(Character.toLowerCase(character));
            } else {
                builder.append(Character.toLowerCase(character));
            }
        }

        if (field.isAnnotationPresent(ManyToOne.class)) {
            String idName = ReflectionUtils.getIdField(field.getType().getDeclaredFields()).getName();
            builder.append("_").append(getEntityName(idName));
        }

        return builder.toString();
    }
}