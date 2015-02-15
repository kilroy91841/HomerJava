package com.homer.fantasy.facade;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.mlb.MLBJSONObject;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arigolub on 2/15/15.
 */
public class PlayerFacadeTest {

    private static PlayerFacade facade;

    @BeforeClass
    public static void beforeClass() {
        facade = new PlayerFacade();
    }

    @Test
    public void createOrUpdatePlayer() {
        Player player = new Player();
        player.setPlayerName("Mike Trout");

        player = facade.createOrUpdatePlayer(player);
        Assert.assertNotNull(player);
    }

    @Test
    public void createOrUpdatePlayer_MLB() throws Exception {
        JSONObject json = new JSONObject();
        String nameFull = "Ari Golub";
        String nameFirst = "Ari";
        String nameLast = "Golub";
        Position position = Position.CATCHER;
        json.put("name_display_first_last", nameFull);
        json.put("name_first", nameFirst);
        json.put("name_last", nameLast);
        json.put("primary_position", position.getPositionId());

        com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(new MLBJSONObject(json));
        Player player = facade.createOrUpdatePlayer(mlbPlayer);
        Assert.assertNotNull(player);
    }
}
