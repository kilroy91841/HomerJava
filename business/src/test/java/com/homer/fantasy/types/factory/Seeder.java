package com.homer.fantasy.types.factory;

import com.homer.fantasy.types.PlayerTest;
import com.homer.fantasy.types.TeamTest;

/**
 * Created by arigolub on 2/4/15.
 */
public class Seeder {

    public static void seedTable(String tableName) {
        if("PLAYER".equals(tableName)) {
            PlayerTest playerTest = new PlayerTest();
            playerTest.seed();
        } else if("TEAM".equals(tableName)) {
            TeamTest teamTest = new TeamTest();
            teamTest.seed();
        }
    }
}
