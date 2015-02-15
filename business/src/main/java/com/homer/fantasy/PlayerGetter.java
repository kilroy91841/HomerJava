package com.homer.fantasy;

import com.homer.SportType;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.mlb.client.MLBClientREST;
import com.homer.mlb.MLBJSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.homer.mlb.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerGetter {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerGetter.class);

    private static HomerDAO dao;

    private static AtomicInteger playerCount;

    public static void main(String[] args) throws SQLException {
        dao = new HomerDAO();
        List<Team> teams = dao.getTeams(SportType.MLB);
        MLBClientREST client = new MLBClientREST();
        com.homer.fantasy.Player p = new com.homer.fantasy.Player();
        playerCount = new AtomicInteger(0);

        for(Team team : teams) {
            client.get40ManRosterAsync(team.getTeamId(), callback);
        }
    }

    private static Callback<JsonNode> callback = new Callback<JsonNode>() {
        @Override
        public void completed(HttpResponse<JsonNode> httpResponse) {
            JSONArray array = httpResponse.getBody()
                    .getObject()
                    .getJSONObject("roster_40")
                    .getJSONObject("queryResults")
                    .getJSONArray("row");
            Player player = null;
            if(array.length() > 0) {
                for(int i = 0; i < array.length(); i++) {
                    playerCount.incrementAndGet();
                    JSONObject obj = (JSONObject)array.get(i);
                    try {
                        player = new Player(new MLBJSONObject(obj));
                        com.homer.fantasy.Player fantasyPlayer = new com.homer.fantasy.Player();
                        fantasyPlayer.setPlayerName(player.getName_display_first_last());
                        fantasyPlayer.setFirstName(player.getName_first());
                        fantasyPlayer.setLastName(player.getName_last());
                        fantasyPlayer.setNameLastFirst(player.getName_display_last_first());
                        fantasyPlayer.setPrimaryPosition(Position.get(player.getPrimary_position()));
                        fantasyPlayer.setMlbPlayerId(player.getPlayer_id());
                        dao.createPlayer(fantasyPlayer);
                    } catch (Exception e) {
                        LOG.error("Unable to create player from obj " + obj);
                    }
                }
            }
            LOG.info("RUNNING TOTAL: " + playerCount.get());
        }

        @Override
        public void failed(UnirestException e) {
            LOG.error("40 man roster get for team failed", e);
        }

        @Override
        public void cancelled() {
            LOG.debug("The request was cancelled");
        }
    };
}
