package com.easy_station.sso.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class Utils {
    public static <T> T copyNonNullProperties(Object source, Object target) {
        String[] propertiesNull = getPropertiesNull(source);
        BeanUtils.copyProperties(source, target, propertiesNull);
        return (T) target;
    }

    public static <T> String[] getPropertiesNull(T value) {
        Class<?> theClass = value.getClass();
        Field[] fields = theClass.getDeclaredFields();

        Stream<Field> valuesFiltered = Arrays.stream(fields).filter(field -> {
            field.setAccessible(true);

            try {
                if (field.get(value) == null) {
                        return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return false;
        });

        return valuesFiltered.map(Field::getName).toArray(String[]::new);
    }
}
