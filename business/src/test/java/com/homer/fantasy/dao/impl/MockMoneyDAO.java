package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IMoneyDAO;

import java.util.*;

/**
 * Created by arigolub on 2/22/15.
 */
public class MockMoneyDAO implements IMoneyDAO {

    private static final Map<String, Money> moneyMap = new HashMap<String, Money>();

    @Override
    public Money saveMoney(Money money) {
        moneyMap.put(money.getMoneyId().toString(), money);
        return money;
    }

    @Override
    public Money getMoney(Money example) {
        return null;
    }

    @Override
    public List<Money> getMoneyForTeam(Team team) {
        List<Money> moneys = new ArrayList<Money>();
        Set<String> keys = moneyMap.keySet();
        for(String s : keys) {
            Money m = moneyMap.get(s);
            if(m.getTeam().getTeamId() == team.getTeamId()) {
                moneys.add(m);
            }
        }
        return moneys;
    }

    @Override
    public Money getMoney(int teamId, int season, Money.MoneyType moneyType) {
        List<Money> moneys = new ArrayList<Money>();
        Set<String> keys = moneyMap.keySet();
        for(String s : keys) {
            Money m = moneyMap.get(s);
            if(m.getTeam().getTeamId() == teamId
                    && m.getSeason() == season
                    && m.getMoneyType().equals(moneyType)) {
                return m;
            }
        }
        return null;
    }

    public static void addMoney(Money money) {
        if(money.getMoneyId() != null) {
            moneyMap.put(money.getMoneyId().toString(), money);
        } else {
            moneyMap.put(money.toString(), money);
        }
    }

    public static void clearMap() {
        moneyMap.clear();
    }
}
