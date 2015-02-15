package com.homer.mlb.parser;

import com.homer.fantasy.Player;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Stats;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/14/15.
 */
public class JSONStatsParser {

    private static final String JSON_QUERY_RESULTS  = "queryResults";
    private static final String JSON_ROW            = "row";
    private static final String JSON_MLBBIO         = "mlb_bio_";
    private static final String JSON_HITTING        = "hitting";
    private static final String JSON_PITCHING       = "pitching";
    private static final String JSON_LAST10         = "_last_10";
    private static final String JSON_MLBINDIVIDUAL  = "mlb_individual_";
    private static final String JSON_GAMELOG        = "_game_log";

    public static List<Stats> parseStats(long playerId, JsonNode json, boolean isBatter) throws Exception {
        List<Stats> stats = null;

        String jsonProperty = isBatter ? JSON_HITTING : JSON_PITCHING;

        JSONArray array = json
                .getObject()
                .getJSONObject(JSON_MLBBIO + jsonProperty + JSON_LAST10)
                .getJSONObject(JSON_MLBINDIVIDUAL + jsonProperty + JSON_GAMELOG)
                .getJSONObject(JSON_QUERY_RESULTS)
                .getJSONArray(JSON_ROW);
        if(array.length() > 0) {
            Player player = new Player();
            player.setPlayerId(playerId);
            stats = new ArrayList<Stats>();
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject)array.get(i);
                stats.add(new Stats(player, new MLBJSONObject(obj)));
            }
        }
        return stats;
    }
}
