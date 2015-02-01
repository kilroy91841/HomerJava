package com.homer.mlb;

import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public interface MLBClient {
    public Player getPlayer(long playerId);

    public List<Stats> getStats(long playerId);

    public List<Player> get40ManRoster(int teamId);
}
