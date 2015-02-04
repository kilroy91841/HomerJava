package com.homer.fantasy.dao;

import com.homer.fantasy.DailyPlayer;
import com.homer.fantasy.Player;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/3/15.
 */
public interface IHomerDAO {

    public Player find(Player example) throws SQLException;

    public boolean createPlayer(String playerName, int positionId, Long mlbPlayerId);

    public boolean createDailyPlayer(Long playerId, Date gameDate, Integer fantasyTeamId, Integer mlbTeamId,
                                     Integer fantasyPlayerStatusId, Integer mlbPlayerStatusId, Integer fantasyPositionId);

//    public List<DailyPlayer> findDailyPlayersByPlayerId(Long playerId) throws Exception;
//
//    public DailyPlayer findMostRecentDailyPlayer(long playerId) throws Exception;
}
