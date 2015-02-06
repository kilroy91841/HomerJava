package com.homer.fantasy;

import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.mlb.MLBClientREST;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerGetter {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerGetter.class);

    public static void main(String[] args) {
        LOG.debug("hi");
        HomerDAO dao = new HomerDAO();
        List<Team> teams = dao.getTeams();
        MLBClientREST client = new MLBClientREST();

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
            List<Player> mlbPlayerList = new ArrayList<Player>();
            Player player = null;
            PlayerFacade facade = new PlayerFacade();
            if(array.length() > 0) {
                mlbPlayerList = new ArrayList<Player>();
                for(int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject)array.get(i);
                    try {
                        player = new Player(new MLBJSONObject(obj));
                        facade.createOrUpdatePlayer(player);
                    } catch (Exception e) {
                        LOG.error("Unable to create player from obj " + obj);
                    }
                    mlbPlayerList.add(player);
                }
            }
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
