package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.dao.IPlayerDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/15/15.
 */
public class MockPlayerDAO implements IPlayerDAO {

    private static Map<String, Player> playerMap = new HashMap<String, Player>();

    @Override
    public Player createOrSave(Player player) {
        playerMap.put(player.getPlayerName(), player);
        return player;
    }

    @Override
    public Player getPlayer(Player example) {
        if(example.getPlayerId() != null) {
            return playerMap.get(example.getPlayerId().toString());
        } else {
            return playerMap.get(example.getPlayerName());
        }
    }

    public List<Player> getPlayersByYear(int season) {
        return new ArrayList<Player>();
    }

    public static void addPlayerToMap(Player player) {
        playerMap.put(player.getPlayerName(), player);
    }

    public static void addPlayerToMapUsingId(Player player) {
        playerMap.put(String.valueOf(player.getPlayerId()), player);
    }

    public static void clearMap() {
        playerMap.clear();
    }
}
