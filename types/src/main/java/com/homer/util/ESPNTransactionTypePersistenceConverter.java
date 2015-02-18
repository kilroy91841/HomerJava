package com.homer.util;

import com.homer.espn.Transaction;

import javax.persistence.AttributeConverter;

import static com.homer.espn.Transaction.getTypeByName;

/**
 * Created by arigolub on 2/18/15.
 */
public class ESPNTransactionTypePersistenceConverter implements
        AttributeConverter<Transaction.Type, String> {

    @Override
    public String convertToDatabaseColumn(Transaction.Type type) {
        return type.getTypeName();
    }

    @Override
    public Transaction.Type convertToEntityAttribute(String s) {
        return getTypeByName(s);
    }
}
