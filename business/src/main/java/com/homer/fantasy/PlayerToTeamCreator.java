package com.homer.fantasy;

import com.homer.fantasy.facade.TransactionFacade;
import com.homer.mlb.MLBClientREST;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by arigolub on 2/6/15.
 */
public class PlayerToTeamCreator {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerToTeamCreator.class);

    public static void main(String[] args) throws Exception {
        String json = readFile("players.json");
        JSONArray array = new JSONObject(json).getJSONArray("players");
        TransactionFacade facade = new TransactionFacade();
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            try {
                long playerId = obj.getLong("mlbPlayerId");
                int fantasyTeam = obj.getInt("fantasyTeamId");
                LOG.debug("player: " + playerId + ", fantasyTeam: " + fantasyTeam);
                Player player = new Player();
                player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(playerId, ThirdPartyPlayerInfo.MLB));
                facade.addFreeAgent(player, fantasyTeam, Position.FANTASYCATCHER);
            } catch(Throwable t) {
                LOG.error("thrown", t);
            }
        }
    }

    private static String readFile(String file) throws IOException
    {
        InputStream stream = PlayerToTeamCreator.class.getClassLoader().getResourceAsStream(file);
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
