package com.homer.baseball;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MLB on 1/25/15.
 */
public class Position {

    public static final Position CATCHER = new Position(2, "CATCHER");
    public static final Position FIRSTBASE = new Position(3, "FIRSTBASE");
    public static final Position SECONDBASE = new Position(4, "SECONDBASE");
    public static final Position THIRDBASE = new Position(5, "THIRDBASE");
    public static final Position SHORTSTOP = new Position(6, "SHORTSTOP");
    public static final Position LEFTFIELD = new Position(7, "LEFTFIELD");
    public static final Position CENTERFIELD = new Position(8, "CENTERFIELD");
    public static final Position RIGHTFIELD = new Position(9, "RIGHTFIELD");
    public static final Position OUTFIELD = new Position(10, "OUTFIELD");
    public static final Position DESIGNATEDHITTER = new Position(11, "DESIGNATEDHITTER");
    public static final Position PITCHER = new Position(1, "PITCHER");
    public static final Position STARTINGPITCHER = new Position(12, "STARTINGPITCHER");
    public static final Position RELIEFPITCHER = new Position(13, "RELIEFPITCHER");

    private static Map<Integer, Position> positionMap = new HashMap<Integer, Position>();

    static {
        positionMap.put(1, PITCHER);
        positionMap.put(2, CATCHER);
        positionMap.put(3, FIRSTBASE);
        positionMap.put(4, SECONDBASE);
        positionMap.put(5, THIRDBASE);
        positionMap.put(6, SHORTSTOP);
        positionMap.put(7, LEFTFIELD);
        positionMap.put(8, CENTERFIELD);
        positionMap.put(9, RIGHTFIELD);
        positionMap.put(10, OUTFIELD);
        positionMap.put(11, DESIGNATEDHITTER);
        positionMap.put(12, STARTINGPITCHER);
        positionMap.put(13, RELIEFPITCHER);
    }

    private String name;
    private int id;

    private Position(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public static Position get(int id) {
        return positionMap.get(id);
    }

    @Override
    public String toString() {
        return "Position{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
