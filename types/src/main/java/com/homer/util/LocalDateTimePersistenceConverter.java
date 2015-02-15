package com.homer.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/14/15.
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
        AttributeConverter<LocalDateTime, java.sql.Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        java.sql.Timestamp databaseValue = entityValue != null ? Timestamp.valueOf(entityValue) : null;
        return databaseValue;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
        return databaseValue != null ? databaseValue.toLocalDateTime() : null;
    }
}
