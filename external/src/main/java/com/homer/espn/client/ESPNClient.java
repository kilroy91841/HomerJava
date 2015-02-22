package com.homer.espn.client;

import com.homer.espn.Player;
import com.homer.espn.Transaction;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public interface ESPNClient {

    public List<Player> getRosterPage();

    public List<Transaction> getTransactions(int teamId, Transaction.Type tranType, String startDate, String endDate);
}
