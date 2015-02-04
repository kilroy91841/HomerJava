package com.homer.dao;

import com.homer.SportType;
import com.homer.fantasy.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by arigolub on 1/31/15.
 */
public class TypesFactory {
    
    public static Team createTeam(ResultSet rs, String tableName) throws SQLException {
        int teamId = rs.getInt(tableName + ".teamId");
        if(rs.wasNull()) {
            return null;
        }
        return new Team(
                teamId,
                rs.getString(tableName + ".teamName"),
                SportType.getSportType(rs.getString(tableName + ".teamType")),
                rs.getString(tableName + ".teamCode")
        );
    }

    public static PlayerHistory createPlayerHistory(ResultSet rs, String tableName) throws SQLException {
        Team draftTeam = null;
        Team keeperTeam = null;

        Integer draftTeamId = rs.getInt("draftTeam.teamId");
        if(!rs.wasNull()) {
            draftTeam = TypesFactory.createTeam(rs, "draftTeam");
        }

        Integer keeperTeamId = rs.getInt("keeperTeam.teamId");
        if(!rs.wasNull()) {
            keeperTeam = TypesFactory.createTeam(rs, "keeperTeam");
        }

        return new PlayerHistory(
                rs.getInt("history.season"),
                rs.getInt("history.salary"),
                rs.getInt("history.keeperSeason"),
                rs.getBoolean("history.minorLeaguer"),
                draftTeam,
                keeperTeam
        );

    }
    
    public static Player createPlayer(ResultSet rs, String tableName) throws SQLException {
        Long playerId = rs.getLong(tableName + ".playerId");
        if(rs.wasNull()) {
            return null;
        }
        Player player = new Player(
            playerId,
            rs.getString(tableName + ".playerName"),
            Position.get(rs.getInt(tableName + ".primaryPositionId"))
        );

        Set<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList = new HashSet<ThirdPartyPlayerInfo>();
        Long mlbPlayerId = rs.getLong(tableName + ".mlbPlayerId");
        if(!rs.wasNull()) {
            thirdPartyPlayerInfoList.add(new ThirdPartyPlayerInfo(mlbPlayerId, ThirdPartyPlayerInfo.MLB));
        }
        player.setThirdPartyPlayerInfoSet(thirdPartyPlayerInfoList);

        return player;
    }
}
