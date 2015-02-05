package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.ThirdPartyPlayerInfo;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/3/15.
 */
public class MockHomerDAO {

    private static Map<Long, Player> findByPlayerIdMap = new HashMap<Long, Player>();
    private static Map<Long, Player> findByMLBPlayerIdMap = new HashMap<Long, Player>();
    private static Map<String, Player> findByPlayerNameMap = new HashMap<String, Player>();

    public Player find(Player example) {
        return findByMLBPlayerIdMap.get(example.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
    }

    public boolean createPlayer(String playerName, int positionId, Long mlbPlayerId) {
        Player player = new Player(1, playerName, Position.get(positionId));
        player.getThirdPartyPlayerInfoSet().add(new ThirdPartyPlayerInfo(mlbPlayerId, ThirdPartyPlayerInfo.MLB));
        findByMLBPlayerIdMap.put(mlbPlayerId, player);
        return true;
    }

    public boolean createDailyPlayer(Long playerId, Date gameDate, Integer fantasyTeamId, Integer mlbTeamId, Integer fantasyPlayerStatusId, Integer mlbPlayerStatusId, Integer fantasyPositionId) {
        return false;
    }
}
