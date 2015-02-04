package com.homer.fantasy;

import com.homer.dao.response.PlayerResponse;
import com.homer.fantasy.dao.BaseballDAO;
import com.homer.fantasy.types.util.DBPreparer;
import com.homer.mlb.MLBJSONObject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by arigolub on 2/2/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerFacadeTest {

    private static PlayerFacade facade = new PlayerFacade();

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("PLAYER"));

        System.out.println("deleting all players from player in db");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();
    }

    @Test
    public void createMLBPlayer() {
        JSONObject json = new JSONObject();
        json.put("name_display_first_last", "Ari Golub");
        json.put("player_id", "012345");
        json.put("primary_position", "8");
        MLBJSONObject mlb = new MLBJSONObject(json);

        boolean success = false;

        try {
            com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(mlb);
            success = facade.createOrUpdatePlayer(mlbPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);

        Player example = new Player();
        example.setPlayerName("Ari Golub");
        Player player = facade.getPlayer(example);
        Assert.assertEquals(new Long("012345"), player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
        Assert.assertEquals("Ari Golub", player.getPlayerName());
    }

    @Test
    public void createMLBPlayerUpdate() {
        JSONObject json = new JSONObject();
        json.put("name_display_first_last", "Lindsay Young");
        json.put("player_id", "012345");
        json.put("primary_position", "8");
        MLBJSONObject mlb = new MLBJSONObject(json);

        boolean success = false;

        try {
            com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(mlb);
            success = facade.createOrUpdatePlayer(mlbPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);
        Player example = new Player();
        example.setPlayerName("Lindsay Young");
        Player player = facade.getPlayer(example);
        Assert.assertEquals(new Long("012345"), player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
        Assert.assertEquals("Lindsay Young", player.getPlayerName());
    }

    @Test
    public void createFantasyPlayer() {
        Player player = new Player();
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);

        boolean success = false;
        try {
            success = facade.createOrUpdatePlayer(player);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);
        Player dbPlayer = facade.getPlayer(player);
        Assert.assertEquals(player.getPlayerName(), dbPlayer.getPlayerName());
        Assert.assertNotNull(dbPlayer.getPlayerId());
    }

    @Test
    public void createFantasyPlayerUpdate() {
        Player player = new Player();
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(8910, ThirdPartyPlayerInfo.MLB));

        boolean success = false;
        try {
            success = facade.createOrUpdatePlayer(player);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);
        Player dbPlayer = facade.getPlayer(player);
        Assert.assertEquals(player.getPlayerName(), dbPlayer.getPlayerName());
        Assert.assertEquals(new Long("8910"), dbPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
    }
}
