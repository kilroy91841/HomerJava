package com.homer.fantasy.dao.impl;

import com.homer.espn.Transaction;
import com.homer.fantasy.dao.IExternalDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/21/15.
 */
public class MockExternalDAO implements IExternalDAO {

    private static Map<String, Transaction> transactionMap = new HashMap<String, Transaction>();

    public static void addTransaction(Transaction transaction) {
        transactionMap.put(transaction.getPlayerName(), transaction);
    }

    public static void clearMap() {
        transactionMap.clear();
    }

    @Override
    public Transaction getTransaction(String nodeText, LocalDateTime time) {
        return null;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public List<Transaction> getPlayerTransactions(String playerName) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction transaction = transactionMap.get(playerName);
        if(transaction != null) {
            transactions.add(transaction);
        }
        return transactions;
    }


}
