package com.homer.mlb.parser;

import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Player;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/14/15.
 */
public class JSONRosterParser {

    private static final String JSON_QUERY_RESULTS  = "queryResults";
    private static final String JSON_ROW            = "row";
    private static final String JSON_ROSTER40       = "roster_40";

    public static List<Player> parseRoster(JsonNode json) throws Exception {
        List<Player> players = null;
        JSONArray array = json
                .getObject()
                .getJSONObject(JSON_ROSTER40)
                .getJSONObject(JSON_QUERY_RESULTS)
                .getJSONArray(JSON_ROW);
        if(array.length() > 0) {
            players = new ArrayList<Player>();
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Player player = new Player(new MLBJSONObject(obj));
                players.add(player);
            }
        }
        return players;
    }
}
