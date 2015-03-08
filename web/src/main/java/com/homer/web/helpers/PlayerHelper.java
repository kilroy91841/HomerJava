package com.homer.web.helpers;

import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.mlb.Stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 3/5/15.
 */
public class PlayerHelper {

    public List<Stats> getYesterdaysStats(Player player) {
        List<Stats> stats = new ArrayList<>();
        if(player.getDailyPlayerInfoList().size() > 0) {
            DailyPlayerInfo dpi = player.getDailyPlayerInfoList().get(0);
            stats = dpi.getStatsList();
        }
        return stats;
    }

    public String getNextYearSalary(PlayerHistory playerHistory) {
        if(playerHistory.isMinorLeaguer()) {
            return String.valueOf(playerHistory.getSalary());
        } else {
            if(playerHistory.getLockedUp()) {
                return String.valueOf(playerHistory.getSalary());
            } else {
                return String.valueOf(playerHistory.getSalary() + 3);
            }
        }
    }
}
