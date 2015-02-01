package com.homer.mlb;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBClientREST implements MLBClient {

    @Override
    public Player getPlayer(long playerId) {
        //http://mlb.com/lookup/json/named.player_info.bam?player_id=545361&sport_code=%27mlb%27
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sport_code", "'mlb'");
        parameters.put("player_id", playerId);
        Player player = null;
        try {

            HttpResponse<JsonNode> response = Unirest.get("http://mlb.com/lookup/json/named.player_info.bam")
                    .queryString(parameters)
                    .asJson();

            JSONObject obj = response.getBody()
                    .getObject()
                    .getJSONObject("player_info")
                    .getJSONObject("queryResults")
                    .getJSONObject("row");
            player = new Player(new MLBJSONObject(obj));

        } catch (UnirestException e) {
            player = null;
            e.printStackTrace();
        } catch (Exception e) {
            player = null;
            e.printStackTrace();
        }
        return player;
    }

    @Override
    public List<Stats> getStats(long playerId) {
        //http://mlb.mlb.com/lookup/json/named.mlb_bio_hitting_last_10.bam?results=200&game_type=%27R%27&season=2014&player_id=545361
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("results", 200);
        parameters.put("game_type", "'R'");
        parameters.put("season", 2014);
        parameters.put("player_id", playerId);
        List<Stats> stats = null;
        try {

            HttpResponse<JsonNode> response = Unirest.get("http://mlb.mlb.com/lookup/json/named.mlb_bio_hitting_last_10.bam")
                    .queryString(parameters)
                    .asJson();

            JSONArray array = response.getBody()
                    .getObject()
                    .getJSONObject("mlb_bio_hitting_last_10")
                    .getJSONObject("mlb_individual_hitting_game_log")
                    .getJSONObject("queryResults")
                    .getJSONArray("row");
            if(array.length() > 0) {
                stats = new ArrayList<Stats>();
                for(int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject)array.get(i);
                    stats.add(new Stats(new MLBJSONObject(obj)));
                }
            }

        } catch (UnirestException e) {
            stats = null;
            e.printStackTrace();
        } catch (Exception e) {
            stats = null;
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    public List<Player> get40ManRoster(int teamId) {
        //http://mlb.mlb.com/lookup/json/named.roster_40.bam
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("team_id", teamId);
        List<Player> players = null;
        try {

            HttpResponse<JsonNode> response = Unirest.get("http://mlb.mlb.com/lookup/json/named.roster_40.bam")
                    .queryString(parameters)
                    .asJson();

            JSONArray array = response.getBody()
                    .getObject()
                    .getJSONObject("roster_40")
                    .getJSONObject("queryResults")
                    .getJSONArray("row");
            if(array.length() > 0) {
                players = new ArrayList<Player>();
                for(int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject)array.get(i);
                    Player player = new Player(new MLBJSONObject(obj));
                    players.add(player);
                }
            }

        } catch (UnirestException e) {
            players = null;
            e.printStackTrace();
        } catch (Exception e) {
            players = null;
            e.printStackTrace();
        }
        return players;
    }

    public Player getPlayerAsync(long playerId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sport_code", "'mlb'");
        parameters.put("player_id", playerId);
        Player player = null;

        Future<HttpResponse<JsonNode>> json = Unirest.get("http://mlb.com/lookup/json/named.player_info.bam")
                .queryString(parameters)
                .asJsonAsync(new Callback<JsonNode>() {
                                 @Override
                                 public void completed(HttpResponse<JsonNode> httpResponse) {
                                     JsonNode node = httpResponse.getBody();
                                     JSONObject obj = node.getObject().getJSONObject("player_info").getJSONObject("queryResults").getJSONObject("row");
                                     try {
                                        Player player = new Player(new MLBJSONObject(obj));
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }

                                 @Override
                                 public void failed(UnirestException e) {

                                 }

                                 @Override
                                 public void cancelled() {

                                 }
                             });

        return player;
    }
}
