package com.homer.fantasy.types;

import com.homer.mlb.MLBClientREST;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Player;
import com.homer.mlb.Stats;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBClientRESTTest {

    @Test
    public void doPlayerTest() {
        MLBClientREST client = new MLBClientREST();
        Player player = client.getPlayer(new Long(545361));
        Assert.assertNotNull(player);

        Player jsonPlayer = getPlayerFromFile();
        Assert.assertEquals(jsonPlayer, player);
    }

    @Test
    public void doStatsTest() {
        MLBClientREST client = new MLBClientREST();
        List<Stats> stats = client.getStats(new Long(545361));
        Assert.assertNotNull(stats);
        Assert.assertTrue(stats.size() > 0);


        List<Stats> jsonStats = getStatsFromFile();
        Assert.assertEquals(jsonStats, stats);
    }

    @Test
    public void doRosterTest() {
        MLBClientREST client = new MLBClientREST();
        List<Player> roster = client.get40ManRoster(147);
        Assert.assertNotNull(roster);
        Assert.assertEquals(40, roster.size());

        List<Player> jsonRoster = getRosterFromFile();
        Assert.assertEquals(jsonRoster, roster);
    }

    private Player getPlayerFromFile() {
        Player player = null;
        try {
            String jsonString = readFile("player_545361.json");
            JSONObject json = new JSONObject(jsonString);
            player = new Player(new MLBJSONObject(json));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    private List<Stats> getStatsFromFile() {
        List<Stats> stats = null;
        try {
            String jsonString = readFile("stats_545361.json");
            JSONArray array = new JSONObject(jsonString).getJSONArray("row");
            stats = new ArrayList<Stats>();
            for(int i = 0; i < array.length(); i++) {
                stats.add(new Stats(new MLBJSONObject(array.getJSONObject(i))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    private List<Player> getRosterFromFile() {
        List<Player> players = null;
        try {
            String rosterString = readFile("roster_147.json");
            JSONArray array = new JSONObject(rosterString).getJSONArray("row");
            players = new ArrayList<Player>();
            for(int i = 0; i < array.length(); i++) {
                players.add(new Player(new MLBJSONObject(array.getJSONObject(i))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    private static String readFile(String file) throws IOException
    {
        InputStream stream = MLBClientREST.class.getClassLoader().getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }
}
