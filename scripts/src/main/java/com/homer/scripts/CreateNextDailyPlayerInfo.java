package com.homer.scripts;

import com.homer.fantasy.facade.PlayerFacade;

/**
 * Created by arigolub on 3/7/15.
 */
public class CreateNextDailyPlayerInfo {

    public static void main(String[] args) {
        PlayerFacade facade = new PlayerFacade();
        boolean success = facade.createNewDailyPlayerInfoForAll();
        System.exit(1);
    }
}
