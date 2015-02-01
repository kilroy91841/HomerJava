package com.homer.baseball;

import com.homer.SportType;
import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
* Created by arigolub on 1/31/15.
*/
public class MoneyTest {

    @Test
    public void testDo() {
        Team team = new Team(1, "Mark Loretta\'s Scars", SportType.FANTASY, "MLS");
        Money money = new Money(team, 2015, Money.MAJORLEAGUEDRAFT, 260);

        DAO dao = new DAO();
        Money dbMoney = dao.get();
        Assert.assertEquals(money, dbMoney);
    }

    public class DAO extends MySQLDAO {

        public Money get() {
            Money money = null;
            Connection connection = getConnection();
            try {

                String sql = "select * from MONEY money, TEAM team " +
                        "where season = 2015 and moneyType = 'MAJORLEAGUEDRAFT' " +
                        "and team.teamId = money.teamId";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Team team = TypesFactory.createTeam(rs, "team");
                    money = new Money(
                        team,
                        rs.getInt("money.season"),
                        Money.MoneyType.get(rs.getString("money.moneyType")),
                        rs.getInt("money.amount")
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            return money;
        }

    }
}
