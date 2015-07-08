package com.homer.espn.client;

import com.homer.espn.Transaction;
import com.homer.fantasy.Team;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 5/2/15.
 */
public class ESPNClientRestApp {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final ESPNClientREST client = new ESPNClientREST();

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2015, 3, 16);
        String dateString = date.format(dateFormatter);

        List<Transaction> masterTransactionList = new ArrayList<Transaction>();

        Team t = new Team(11);
        List<Transaction> adds = client.getTransactions(t.getTeamId(), Transaction.ADD, dateString, dateString);
        if(adds != null) {
            masterTransactionList.addAll(adds);
        }
        List<Transaction> drops = client.getTransactions(t.getTeamId(), Transaction.DROP, dateString, dateString);
        if(drops != null) {
            masterTransactionList.addAll(drops);
        }
        List<Transaction> trades = client.getTransactions(t.getTeamId(), Transaction.TRADE, dateString, dateString);
        if(trades != null) {
            masterTransactionList.addAll(trades);
        }
        masterTransactionList.sort((t1, t2) -> t1.getTime().compareTo(t2.getTime()));

        System.out.println(masterTransactionList);
    }
}
