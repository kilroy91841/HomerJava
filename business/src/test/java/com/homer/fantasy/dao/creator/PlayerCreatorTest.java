package com.homer.fantasy.dao.creator;

import com.homer.dao.MySQLDAO;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.mlb.MLBJSONObject;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerCreatorTest {

    private HomerDAO dao = new HomerDAO();

    @Test
    public void createPlayerTest() {
        JSONObject json = new JSONObject();
        json.put("name_display_first_last", "Ari Golub");
        json.put("player_id", "012345");
        json.put("primary_position", "8");
        MLBJSONObject mlb = new MLBJSONObject(json);

        boolean success = false;

        try {
            PlayerCreator playerCreator = new PlayerCreator(dao.getConnection());
            com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(mlb);
            success = playerCreator.create(
                    mlbPlayer.getName_display_first_last(),
                    mlbPlayer.getPrimary_position(),
                    mlbPlayer.getPlayer_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);
    }

    @Test
    public void createPlayerTest2() {
        boolean success = false;

        try {
            PlayerCreator playerCreator = new PlayerCreator(dao.getConnection());
            success = playerCreator.create(
                    "Ari Golub",
                    1,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(success);
    }
}
