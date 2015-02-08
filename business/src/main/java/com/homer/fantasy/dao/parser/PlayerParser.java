package com.homer.fantasy.dao.parser;

import com.homer.PlayerStatus;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/8/15.
 */
public class PlayerParser {

    public static Player create(ResultSet rs) throws Exception {
        Player player = null;
        if(rs.first()) {
            player = Player.create(rs, "player");

            rs.beforeFirst();
            while(rs.next()) {
                Team fantasyTeam = Team.create(rs, "fantasyTeam");
                Team mlbTeam = Team.create(rs, "mlbTeam");
                Date gameDate = rs.getDate("playerToTeam.gameDate");
                if(gameDate != null) {
                    DailyPlayerInfo info = new DailyPlayerInfo(fantasyTeam, mlbTeam,
                            gameDate,
                            Position.get(rs.getInt("playerToTeam.fantasyPositionId")),
                            PlayerStatus.get(rs.getString("playerToTeam.fantasyPlayerStatusCode")),
                            PlayerStatus.get(rs.getString("playerToTeam.mlbPlayerStatusCode")),
                            null
                    );
                    player.addDailyPlayerInfoList(info);
                }
            }
        }
        return player;
    }

}
