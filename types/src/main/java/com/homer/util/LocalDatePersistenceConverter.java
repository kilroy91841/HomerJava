package com.homer.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/14/15.
 */
@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements
        AttributeConverter<LocalDate, java.sql.Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date != null ? date.toLocalDate() : null;
    }
}
