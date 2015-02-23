package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Team;
import com.homer.fantasy.Trade;
import com.homer.fantasy.dao.ITradeDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by arigolub on 2/22/15.
 */
public class MockTradeDAO implements ITradeDAO {

    private static final Map<Integer, Trade> tradeMap = new HashMap<Integer, Trade>();
    private static int counter = 100;

    @Override
    public Trade saveTrade(Trade trade) {
        if(trade.getTradeId() == 0) {
            trade.setTradeId(counter++);
        }
        tradeMap.put(trade.getTradeId(), trade);
        return trade;
    }

    @Override
    public Trade getTradeById(int tradeId) {
        Set<Integer> keys = tradeMap.keySet();
        for(Integer i : keys) {
            if(tradeMap.get(i).getTradeId() == tradeId) {
                return tradeMap.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Trade> getTradesForTeam(Team team) {
        return null;
    }

    public static void addTrade(Trade trade) {
        tradeMap.put(trade.getTradeId(), trade);
    }

    public static void clearMap() {
        tradeMap.clear();
    }
}
