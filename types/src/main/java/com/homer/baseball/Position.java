package com.homer.baseball;

import com.homer.SportType;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MLB on 1/25/15.
 */
public class Position {

    public static final Position PITCHER = new Position(1, "PITCHER", SportType.getSportType("MLB"), "P");
    public static final Position CATCHER = new Position(2, "CATCHER", SportType.getSportType("MLB"), "C");
    public static final Position FIRSTBASE = new Position(3, "FIRSTBASE", SportType.getSportType("MLB"), "1B");
    public static final Position SECONDBASE = new Position(4, "SECONDBASE", SportType.getSportType("MLB"), "2B");
    public static final Position THIRDBASE = new Position(5, "THIRDBASE", SportType.getSportType("MLB"), "3B");
    public static final Position SHORTSTOP = new Position(6, "SHORTSTOP", SportType.getSportType("MLB"), "SS");
    public static final Position LEFTFIELD = new Position(7, "LEFTFIELD", SportType.getSportType("MLB"), "LF");
    public static final Position CENTERFIELD = new Position(8, "CENTERFIELD", SportType.getSportType("MLB"), "CF");
    public static final Position RIGHTFIELD = new Position(9, "RIGHTFIELD", SportType.getSportType("MLB"), "RF");
    public static final Position DESIGNATEDHITTER = new Position(10, "DESIGNATEDHITTER", SportType.getSportType("MLB"), "DH");
    public static final Position STARTINGPITCHER = new Position(11, "STARTINGPITCHER", SportType.getSportType("FANTASY"), "SP");
    public static final Position RELIEFPITCHER = new Position(12, "RELIEFPITCHER", SportType.getSportType("FANTASY"), "RP");

    private static Map<Integer, Position> positionMap = new HashMap<Integer, Position>();

    static {
        positionMap.put(PITCHER.getPositionId(), PITCHER);
        positionMap.put(CATCHER.getPositionId(), CATCHER);
        positionMap.put(FIRSTBASE.getPositionId(), FIRSTBASE);
        positionMap.put(SECONDBASE.getPositionId(), SECONDBASE);
        positionMap.put(THIRDBASE.getPositionId(), THIRDBASE);
        positionMap.put(SHORTSTOP.getPositionId(), SHORTSTOP);
        positionMap.put(LEFTFIELD.getPositionId(), LEFTFIELD);
        positionMap.put(CENTERFIELD.getPositionId(), CENTERFIELD);
        positionMap.put(RIGHTFIELD.getPositionId(), RIGHTFIELD);
        positionMap.put(DESIGNATEDHITTER.getPositionId(), DESIGNATEDHITTER);
        positionMap.put(STARTINGPITCHER.getPositionId(), STARTINGPITCHER);
        positionMap.put(RELIEFPITCHER.getPositionId(), RELIEFPITCHER);
    }

    private Integer positionId;
    private String positionName;
    private SportType positionType;
    private String positionCode;

    private Position(Integer positionId, String positionName, SportType positionType, String positionCode) {
        setPositionId(positionId);
        setPositionName(positionName);
        setPositionType(positionType);
        setPositionCode(positionCode);
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public SportType getPositionType() {
        return positionType;
    }

    public void setPositionType(SportType positionType) {
        this.positionType = positionType;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public static Position get(Integer id) {
        return positionMap.get(id);
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                ", positionType=" + positionType +
                ", positionCode='" + positionCode + '\'' +
                '}';
    }
}
