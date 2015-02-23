package com.homer.fantasy.dao;

import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public interface IMoneyDAO {

    static final Logger LOG = LoggerFactory.getLogger(IMoneyDAO.class);

    public Money saveMoney(Money money);

    public Money getMoney(Money example);

    public List<Money> getMoneyForTeam(Team team);

    public Money getMoney(int teamId, int season, Money.MoneyType moneyType);

    public static class FACTORY {

        private static IMoneyDAO instance = null;

        public static IMoneyDAO getInstance() {
            if(instance == null) {
                synchronized (IMoneyDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IMoneyDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IMoneyDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
