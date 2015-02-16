package com.homer.fantasy;

import com.homer.SportType;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.facade.GameFacade;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.mlb.Game;
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
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerGetter {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerGetter.class);

    private static HomerDAO dao = new HomerDAO();
    private static final PlayerFacade facade = new PlayerFacade();
    private static final GameFacade gameFacade = new GameFacade();

    private static AtomicInteger playerCount;

    public static void main(String[] args) throws SQLException {
        List<Team> teams = dao.getTeams(SportType.MLB);
        MLBClientREST client = new MLBClientREST();
        com.homer.fantasy.Player p = new com.homer.fantasy.Player();
        playerCount = new AtomicInteger(0);

        if(false) {
            for (Team team : teams) {
                client.get40ManRosterAsync(team.getTeamId(), callback);
//            List<Player> players = client.get40ManRoster(team.getTeamId());
//
//            for(Player player : players) {
//                try {
//                    facade.createOrUpdatePlayer(player);
//                } catch (Exception e) {
//                    LOG.error("Unable to create player from obj " + player);
//                }
//            }
            }
        }

        if(true) {
            LocalDate startDate = LocalDate.of(2014, 3, 15);
            LocalDate endDate = LocalDate.of(2014, 10, 15);
            for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                List<Game> games = client.getSchedule(date);
                if(games != null) {
                    for (Game g : games) {
                        gameFacade.createOrUpdateGame(g);
                    }
                }
            }
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
            if(array.length() > 0) {
                for(int i = 0; i < array.length(); i++) {
                    playerCount.incrementAndGet();
                    JSONObject obj = (JSONObject)array.get(i);
                    try {
                        com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(new MLBJSONObject(obj));
                        facade.createOrUpdatePlayer(mlbPlayer);
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
