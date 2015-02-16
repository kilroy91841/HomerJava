package com.homer.mlb.parser;

import com.homer.mlb.Game;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Player;
import com.mashape.unirest.http.JsonNode;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/14/15.
 */
public class JSONScheduleParser {

    private static final Logger LOG = LoggerFactory.getLogger(JSONScheduleParser.class);

    private static final String JSON_DATA   = "data";
    private static final String JSON_GAMES  = "games";
    private static final String JSON_GAME   = "game";

    public static List<Game> parseSchedule(JsonNode node) throws Exception {
        List<Game> games = null;
        JSONArray array = node
                .getObject()
                .getJSONObject(JSON_DATA)
                .getJSONObject(JSON_GAMES)
                .getJSONArray(JSON_GAME);
        if(array.length() > 0) {
            games = new ArrayList<Game>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject)array.get(i);
                try {
                    games.add(new Game(new MLBJSONObject(obj)));
                } catch(Exception e) {
                    LOG.error("Error parsing game [json=" + obj + "]", e);
                }
            }
        }
        return games;
    }
}
