package com.homer.espn;

import com.homer.espn.client.ESPNClient;

import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class MockESPNClient implements ESPNClient {
    @Override
    public List<Player> getRosterPage() {
        return null;
    }

    @Override
    public List<Transaction> getTransactions(int teamId, Transaction.Type tranType, String startDate, String endDate) {
        return null;
    }
}
