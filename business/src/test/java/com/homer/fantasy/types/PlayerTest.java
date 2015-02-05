package com.homer.fantasy.types;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.types.util.DBPreparer;
import com.homer.util.PropertyRetriever;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 1/29/15.
 */
public class PlayerTest {

    private static HomerDAO dao;

    @BeforeClass
    public static void prepare() {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("VULTURE", "TRADE", "PLAYERTOTEAM", "PLAYER"));

        System.out.println("deleting all players from player in db ");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();
    }

    @Test
    public void saveAndFind() {
        Player player = new Player();
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(545361, ThirdPartyPlayerInfo.MLB));

        dao = new HomerDAO();
        dao.createPlayer(player);

        Player dbPlayer = dao.findByExample(player);
        Assert.assertEquals(player, dbPlayer);
    }

    public void seed() {
        saveAndFind();
    }

}
