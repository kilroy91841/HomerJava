package com.homer.dao;

import com.homer.SportType;
import com.homer.baseball.Player;
import com.homer.baseball.Position;
import com.homer.baseball.Team;
import com.homer.baseball.ThirdPartyPlayerInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public static Player createPlayer(ResultSet rs, String tableName) throws SQLException {
        long playerId = rs.getLong(tableName + ".playerId");
        if(rs.wasNull()) {
            return null;
        }
        Player player = new Player(
            playerId,
            rs.getString(tableName + ".playerName"),
            Position.get(rs.getInt(tableName + ".primaryPositionId"))
        );

        List<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList = new ArrayList<ThirdPartyPlayerInfo>();
        Long mlbPlayerId = rs.getLong(tableName + ".mlbPlayerId");
        if(rs.wasNull()) {
            mlbPlayerId = null;
        } else {
            thirdPartyPlayerInfoList.add(new ThirdPartyPlayerInfo(player, mlbPlayerId, ThirdPartyPlayerInfo.MLB));
        }
        player.setThirdPartyPlayerInfoList(thirdPartyPlayerInfoList);

        return player;
    }
}
