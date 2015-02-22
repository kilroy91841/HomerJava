package com.homer.mlb.client;

import com.homer.mlb.Game;
import com.homer.mlb.Player;
import com.homer.mlb.Stats;
import com.homer.util.Factory;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public interface MLBClient {

    static final Logger LOG = LoggerFactory.getLogger(MLBClient.class);

    public Player getPlayer(long playerId);
    public void getPlayerAsync(long playerId, Callback<JsonNode> callback);

    public List<Stats> getStats(long playerId, boolean isBatter);
    public void getStatsAsync(long playerId, boolean isBatter, Callback<JsonNode> callback);

    public List<Player> get40ManRoster(int teamId);
    public void get40ManRosterAsync(int teamId, Callback<JsonNode> callback);

    public List<Game> getSchedule(LocalDate date);
    public void getScheduleAsync(LocalDate date, Callback<JsonNode> callback);

    public static class FACTORY {

        private static MLBClient instance = null;

        public static MLBClient getInstance() {
            if(instance == null) {
                synchronized (MLBClient.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(MLBClient.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of MLBClient", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
