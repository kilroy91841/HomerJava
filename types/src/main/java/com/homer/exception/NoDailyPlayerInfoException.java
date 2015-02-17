package com.homer.exception;

import com.homer.fantasy.Player;

/**
 * Created by arigolub on 2/16/15.
 */
public class NoDailyPlayerInfoException extends Exception {

    public NoDailyPlayerInfoException(Player p) {
        super("Player with id " + p.getPlayerId() + " did not have any dailyPlayerInfo when at least one is always expected");
    }
}
