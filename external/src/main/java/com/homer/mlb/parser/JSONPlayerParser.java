package com.homer.mlb.parser;

import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Player;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

/**
 * Created by arigolub on 2/14/15.
 */
public class JSONPlayerParser {

    private static final String JSON_QUERY_RESULTS  = "queryResults";
    private static final String JSON_PLAYER_INFO    = "player_info";
    private static final String JSON_ROW            = "row";

    public static Player parsePlayer(JsonNode json) throws Exception {
        JSONObject obj = json
                .getObject()
                .getJSONObject(JSON_PLAYER_INFO)
                .getJSONObject(JSON_QUERY_RESULTS)
                .getJSONObject(JSON_ROW);
        return new Player(new MLBJSONObject(obj));
    }
}
