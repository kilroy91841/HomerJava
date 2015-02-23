package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IMoneyDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class HibernateMoneyDAO extends HomerDAO implements IMoneyDAO {

    @Override
    public Money saveMoney(Money money) {
        LOG.debug("BEGIN: saveMoney [money=" + money + "]");

        //The primary key for money is moneyId, but the logical primary key is teamId-season-moneyType
        //Check if it already exists before creating a new one. If it does, set the id on the new money
        //so that it will update.
        Money exists = getMoney(money);
        if(exists != null) {
            //Money exists, set properties on money to be saved
            money.setMoneyId(exists.getMoneyId());
            money.setTeam(exists.getTeam());
            money.setSeason(exists.getSeason());
            money.setMoneyType(exists.getMoneyType());
        }

        boolean success = saveOrUpdate(money);
        if(!success) {
            money = null;
        }

        LOG.debug("END: saveMoney [money=" + money + "]");
        return money;
    }

    @Override
    public Money getMoney(Money money) {
        LOG.debug("BEGIN: getMoney [example=" + money + "]");

        Money dbMoney = null;
        if(money.getMoneyId() != null) {
            dbMoney = findUniqueById(money.getMoneyId(), Money.class);
        } else {
            dbMoney = getMoney(money.getTeam().getTeamId(), money.getSeason(), money.getMoneyType());
        }

        LOG.debug("END: getMoney [money=" + dbMoney + "]");
        return dbMoney;
    }

    @Override
    public Money getMoney(int teamId, int season, Money.MoneyType moneyType) {
        LOG.debug("BEGIN: getMoney [teamId=" + teamId + ", season=" + season + ", moneyType=" + moneyType + "]");

        Money example = new Money();
        example.setTeam(new Team(teamId));
        example.setMoneyType(moneyType);
        example.setSeason(season);

        Money money = findUniqueByExample(example, Money.class);

        LOG.debug("END: getMoney [money=" + money + "]");
        return money;
    }

    @Override
    public List<Money> getMoneyForTeam(Team team) {
        LOG.debug("BEGIN: getMoneyForTeam [team=" + team + "]");

        Money example = new Money();
        example.setTeam(team);
        List<Money> moneys = findListByExample(example, Money.class);

        LOG.debug("END: getMoneyForTeam [moneys=" + moneys + "]");
        return moneys;
    }
}
