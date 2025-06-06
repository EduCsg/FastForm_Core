package com.fastForm.core.Utils;

import java.lang.reflect.Field;
import java.util.List;

public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty() || list.getFirst() == null;
    }

    public static boolean isEmpty(Object obj) {
        try {
            return obj == null || obj.toString().isEmpty() || areAllFieldsNull(obj);
        } catch (IllegalAccessException e) {
            return true;
        }
    }

    public static boolean notEmpty(String value) {
        return value != null && !value.isEmpty() && !value.isBlank();
    }

    public static boolean notEmpty(List<?> list) {
        return list != null && !list.isEmpty() && list.getFirst() != null;
    }

    private static boolean areAllFieldsNull(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(obj) != null) return false;
        }
        return true;
    }

}
