package com.homer.fantasy.facade;

import com.homer.exception.DisallowedTransactionException;
import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.MockMoneyDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MoneyFacadeTest {

    private static Money team1MLMoney;
    private static Money team1FAAMoney;
    private static Money team2MLMoney;
    private static Money team2FAAMoney;
    private static Team team1;
    private static Team team2;

    private static MoneyFacade facade;

    @BeforeClass
    public static void setup() {
        facade = new MoneyFacade();

        team1 = new Team(1);
        team2 = new Team(2);

        team1MLMoney = new Money();
        team1MLMoney.setMoneyId(1L);
        team1MLMoney.setTeam(team1);
        team1MLMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        team1MLMoney.setSeason(2015);
        team1MLMoney.setAmount(260);

        team1FAAMoney = new Money();
        team1FAAMoney.setMoneyId(2L);
        team1FAAMoney.setTeam(team1);
        team1FAAMoney.setMoneyType(Money.MoneyType.FREEAGENTAUCTION);
        team1FAAMoney.setSeason(2015);
        team1FAAMoney.setAmount(120);

        team2MLMoney = new Money();
        team2MLMoney.setMoneyId(3L);
        team2MLMoney.setTeam(team2);
        team2MLMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        team2MLMoney.setSeason(2015);
        team2MLMoney.setAmount(230);

        team2FAAMoney = new Money();
        team2FAAMoney.setMoneyId(4L);
        team2FAAMoney.setTeam(team2);
        team2FAAMoney.setMoneyType(Money.MoneyType.FREEAGENTAUCTION);
        team2FAAMoney.setSeason(2015);
        team2FAAMoney.setAmount(80);

        MockMoneyDAO.addMoney(team1MLMoney);
        MockMoneyDAO.addMoney(team1FAAMoney);
        MockMoneyDAO.addMoney(team2MLMoney);
        MockMoneyDAO.addMoney(team2FAAMoney);
    }

    @Test
    public void a_getMoneyForTeam() {
        List<Money> moneys = facade.getMoneyForTeam(team1);
        Assert.assertNotNull(moneys);
        Assert.assertEquals(2, moneys.size());
    }

    @Test
    public void b_transferAllowed() {
        Money tradeMoney = new Money();
        tradeMoney.setTeam(team1);
        tradeMoney.setAmount(5);
        tradeMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        tradeMoney.setSeason(2015);
        Money team2NewMoney = null;
        try {
            team2NewMoney = facade.transferMoney(tradeMoney, team2);
            Assert.assertNotNull(team2NewMoney);
            Assert.assertEquals(235, (int)team2NewMoney.getAmount());
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void c_transferNotAllowed() {
        Money tradeMoney = new Money();
        tradeMoney.setTeam(team2);
        tradeMoney.setAmount(16);
        tradeMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        tradeMoney.setSeason(2015);
        try {
            facade.transferMoney(tradeMoney, team1);
            Assert.fail();
        } catch(DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void d_transferNotAllowed() {
        Money tradeMoney = new Money();
        tradeMoney.setTeam(team2);
        tradeMoney.setAmount(81);
        tradeMoney.setMoneyType(Money.MoneyType.FREEAGENTAUCTION);
        tradeMoney.setSeason(2015);
        try {
            facade.transferMoney(tradeMoney, team1);
            Assert.fail();
        } catch(DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }
    }
}
