package com.homer.fantasy.dao;

import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernateMoneyDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Random;

/**
 * Created by arigolub on 2/22/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateMoneyDAOTest {

    private static final HibernateMoneyDAO dao = new HibernateMoneyDAO();

    private static final Random rand = new Random();
    private static long moneyId;
    private static Team team;
    private static int season = 2015;
    private static int amount = 260;

    @BeforeClass
    public static void setup() {
        //tradeId = rand.nextInt((10000 - 0) + 1) + 0;
        team = new Team();
        team.setTeamId(1);
    }

    @Test
    public void a_save() {
        Money money = new Money();
        money.setTeam(team);
        money.setSeason(season);
        money.setAmount(amount);
        money.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);

        money = dao.saveMoney(money);
        Assert.assertNotNull(money);
        Assert.assertEquals(team, money.getTeam());
        Assert.assertEquals(season, money.getSeason());
        Assert.assertEquals(amount, (int)money.getAmount());
        Assert.assertEquals(Money.MoneyType.MAJORLEAGUEDRAFT, money.getMoneyType());
        Assert.assertNotNull(money.getMoneyId());

        moneyId = money.getMoneyId();
    }

    @Test
    public void b_update() {
        Money money = new Money();
        money.setMoneyId(moneyId);
        money.setAmount(1);

        money = dao.saveMoney(money);
        Assert.assertEquals(moneyId, (long)money.getMoneyId());
        Assert.assertEquals(1, (int)money.getAmount());
    }

    @Test
    public void c_get() {
        Money money = new Money();
        money.setMoneyId(moneyId);
        money = dao.getMoney(money);

        Assert.assertNotNull(money);
        Assert.assertNotNull(money.getTeam());
        Assert.assertNotNull(money.getMoneyType());
        Assert.assertEquals(2015, money.getSeason());
        Assert.assertTrue(money.getAmount() > 0);

        money = new Money();
        money.setTeam(team);
        money.setSeason(season);
        money.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        money = dao.getMoney(money);

        Assert.assertNotNull(money);
        Assert.assertNotNull(money.getTeam());
        Assert.assertNotNull(money.getMoneyType());
        Assert.assertEquals(2015, money.getSeason());
        Assert.assertTrue(money.getAmount() > 0);

        money = dao.getMoney(team.getTeamId(), season, Money.MoneyType.MAJORLEAGUEDRAFT);
        Assert.assertNotNull(money);
        Assert.assertNotNull(money.getTeam());
        Assert.assertNotNull(money.getMoneyType());
        Assert.assertEquals(2015, money.getSeason());
        Assert.assertTrue(money.getAmount() > 0);
    }
}
