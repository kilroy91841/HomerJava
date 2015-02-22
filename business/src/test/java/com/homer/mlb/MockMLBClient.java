package com.homer.mlb;

import com.homer.mlb.*;
import com.homer.mlb.Player;
import com.homer.mlb.client.MLBClient;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class MockMLBClient implements MLBClient {
    @Override
    public Player getPlayer(long playerId) {
        return null;
    }

    @Override
    public void getPlayerAsync(long playerId, Callback<JsonNode> callback) {

    }

    @Override
    public List<Stats> getStats(long playerId, boolean isBatter) {
        return null;
    }

    @Override
    public void getStatsAsync(long playerId, boolean isBatter, Callback<JsonNode> callback) {

    }

    @Override
    public List<Player> get40ManRoster(int teamId) {
        return null;
    }

    @Override
    public void get40ManRosterAsync(int teamId, Callback<JsonNode> callback) {

    }

    @Override
    public List<Game> getSchedule(LocalDate date) {
        return null;
    }

    @Override
    public void getScheduleAsync(LocalDate date, Callback<JsonNode> callback) {

    }
}
