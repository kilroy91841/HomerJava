package com.homer.mlb.client;

import com.homer.mlb.Game;
import com.homer.mlb.Player;
import com.homer.mlb.Stats;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public interface MLBClient {
    public Player getPlayer(long playerId);
    public void getPlayerAsync(long playerId, Callback<JsonNode> callback);

    public List<Stats> getStats(long playerId, boolean isBatter);
    public void getStatsAsync(long playerId, boolean isBatter, Callback<JsonNode> callback);

    public List<Player> get40ManRoster(int teamId);
    public void get40ManRosterAsync(int teamId, Callback<JsonNode> callback);

    public List<Game> getSchedule(LocalDate date);
    public void getScheduleAsync(LocalDate date, Callback<JsonNode> callback);
}
