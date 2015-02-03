package com.homer.fantasy;

import com.homer.dao.response.PlayerResponse;
import com.homer.mlb.MLBJSONObject;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerFacadeTest {

    private static PlayerFacade facade = new PlayerFacade();

    @Test
    public void createPlayerTest() {
        JSONObject json = new JSONObject();
        json.put("name_display_first_last", "Ari Golub");
        json.put("player_id", "012345");
        json.put("primary_position", "8");
        MLBJSONObject mlb = new MLBJSONObject(json);

        PlayerResponse response = null;

        try {
            com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(mlb);
            response = facade.createPlayer(mlbPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(PlayerResponse.SUCCESS, response.getStatus());
    }
}
