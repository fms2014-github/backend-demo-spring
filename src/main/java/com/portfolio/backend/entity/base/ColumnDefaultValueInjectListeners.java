package com.portfolio.backend.entity.base;

import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Slf4j
public class ColumnDefaultValueInjectListeners {

    @PrePersist
    public void prePersist(Object entity){
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(ColumnDefault.class)) {
                try{
                    field.setAccessible(true);

                    if (field.get(entity) == null) {
                        String defaultValue = field.getAnnotation(ColumnDefault.class).value();
                        Class<?> fieldType = field.getType();

                        Object ConvertValue = convertSqlValueToEntityValue(defaultValue, fieldType);
                        if (ConvertValue != null) {
                            field.set(entity, ConvertValue);
                        } else {
                            log.debug("ColumnDefault 처리 대상이 아닙니다. @PrePersist를 따로 선언해 주세요.");
                        }
                    }

                } catch (IllegalAccessException e) {
                    log.error("ColumnDefault annotation 처리 중 에러 발생", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Object convertSqlValueToEntityValue(String sqlValue, Class<?> fieldType) {
        // Converts SQL default value to entity value
        if (fieldType == String.class) {
            return sqlValue.replace("'", "");
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(sqlValue);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(sqlValue);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(sqlValue);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(sqlValue);
        } else if (fieldType == Float.class || fieldType == float.class) {
            return Float.parseFloat(sqlValue);
        } else if (fieldType == Short.class || fieldType == short.class) {
            return Short.parseShort(sqlValue);
        } else if (fieldType == Byte.class || fieldType == byte.class) {
            return Byte.parseByte(sqlValue);
        } else if (fieldType == Character.class || fieldType == char.class) {
            return sqlValue.charAt(0);
        } else if (fieldType == BigDecimal.class) {
            return new BigDecimal(sqlValue);
        } else {
            return null;
        }
    }
}
