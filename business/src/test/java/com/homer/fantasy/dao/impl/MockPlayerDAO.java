package com.homer.fantasy.dao.impl;

import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IPlayerDAO;

import java.time.LocalDate;
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
        if(player.getPlayerId() != null) {
            addPlayerToMapUsingId(player);
        } else {
            addPlayerToMap(player);
        }
        return player;
    }

    @Override
    public Player getPlayer(Player example) {
        if(example.getPlayerId() != null) {
            return playerMap.get(example.getPlayerId().toString());
        } else if (example.getEspnPlayerId() != null) {
            return playerMap.get(example.getEspnPlayerId().toString());
        } else if (example.getEspnPlayerName() != null) {
            return playerMap.get(example.getEspnPlayerName());
        } else {
            return playerMap.get(example.getPlayerName());
        }
    }

    public List<Player> getPlayersByYear(int season) {
        return new ArrayList<Player>();
    }

    @Override
    public List<Player> getPlayersOnTeamForDate(Team team, LocalDate date) {
        List<Player> players = new ArrayList<Player>();
        for(Player p : playerMap.values()) {
            try {
                if(team.getTeamId() == p.getCurrentFantasyTeam().getTeamId() && date.equals(p.getDailyPlayerInfoList().get(0).getDate())) {
                    players.add(p);
                }
            } catch (NoDailyPlayerInfoException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    public static void addPlayerToMap(Player player) {
        playerMap.put(player.getPlayerName(), player);
    }

    public static void addPlayerToMapUsingId(Player player) {
        playerMap.put(String.valueOf(player.getPlayerId()), player);
    }

    public static void addPlayerToMapUsingESPNPlayerName(Player player) {
        playerMap.put(player.getEspnPlayerName(), player);
    }

    public static void addPlayerToMapUsingESPNPlayerId(Player player) {
        playerMap.put(String.valueOf(player.getEspnPlayerId()), player);
    }

    public static void clearMap() {
        playerMap.clear();
    }
}
