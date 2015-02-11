package com.homer.fantasy;

import com.homer.SportType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MLB on 1/25/15.
 */
@Entity
@Table(name="POSITION")
public class Position {

    public static final Position STARTINGPITCHER = new Position(1, "STARTINGPITCHER", SportType.getSportType("MLB"), "P");
    public static final Position CATCHER = new Position(2, "CATCHER", SportType.getSportType("MLB"), "C");
    public static final Position FIRSTBASE = new Position(3, "FIRSTBASE", SportType.getSportType("MLB"), "1B");
    public static final Position SECONDBASE = new Position(4, "SECONDBASE", SportType.getSportType("MLB"), "2B");
    public static final Position THIRDBASE = new Position(5, "THIRDBASE", SportType.getSportType("MLB"), "3B");
    public static final Position SHORTSTOP = new Position(6, "SHORTSTOP", SportType.getSportType("MLB"), "SS");
    public static final Position LEFTFIELD = new Position(7, "LEFTFIELD", SportType.getSportType("MLB"), "LF");
    public static final Position CENTERFIELD = new Position(8, "CENTERFIELD", SportType.getSportType("MLB"), "CF");
    public static final Position RIGHTFIELD = new Position(9, "RIGHTFIELD", SportType.getSportType("MLB"), "RF");
    public static final Position DESIGNATEDHITTER = new Position(10, "DESIGNATEDHITTER", SportType.getSportType("MLB"), "DH");
    public static final Position RELIEFPITCHER = new Position(11, "RELIEFPITCHER", SportType.getSportType("MLB"), "RP");
    public static final Position FANTASYUTILITY =  new Position(110, "UTILITY", SportType.getSportType("FANTASY"), "U");
    public static final Position FANTASYCATCHER =  new Position(102, "CATCHER", SportType.getSportType("FANTASY"), "C");
    public static final Position FANTASYFIRSTBASE =  new Position(103, "FIRSTBASE", SportType.getSportType("FANTASY"), "1B");
    public static final Position FANTASYSECONDBASE =  new Position(104, "SECONDBASE", SportType.getSportType("FANTASY"), "2B");
    public static final Position FANTASYTHIRDBASE =  new Position(105, "THIRDBASE", SportType.getSportType("FANTASY"), "3B");
    public static final Position FANTASYSHORTSTOP =  new Position(106, "SHORTSTOP", SportType.getSportType("FANTASY"), "SS");
    public static final Position FANTASYOUTFIELD =  new Position(107, "OUTFIELD", SportType.getSportType("FANTASY"), "OF");
    public static final Position FANTASYMIDDLEINFIELD =  new Position(108, "MIDDLEINFIELD", SportType.getSportType("FANTASY"), "MI");
    public static final Position FANTASYCORNERINFIELD =  new Position(109, "CORNERINFIELD", SportType.getSportType("FANTASY"), "CI");
    public static final Position FANTASYPITCHER =  new Position(101, "PITCHER", SportType.getSportType("FANTASY"), "P");

    private static Map<Integer, Position> positionMap = new HashMap<Integer, Position>();

    static {
        positionMap.put(STARTINGPITCHER.getPositionId(), STARTINGPITCHER);
        positionMap.put(CATCHER.getPositionId(), CATCHER);
        positionMap.put(FIRSTBASE.getPositionId(), FIRSTBASE);
        positionMap.put(SECONDBASE.getPositionId(), SECONDBASE);
        positionMap.put(THIRDBASE.getPositionId(), THIRDBASE);
        positionMap.put(SHORTSTOP.getPositionId(), SHORTSTOP);
        positionMap.put(LEFTFIELD.getPositionId(), LEFTFIELD);
        positionMap.put(CENTERFIELD.getPositionId(), CENTERFIELD);
        positionMap.put(RIGHTFIELD.getPositionId(), RIGHTFIELD);
        positionMap.put(DESIGNATEDHITTER.getPositionId(), DESIGNATEDHITTER);
        positionMap.put(RELIEFPITCHER.getPositionId(), RELIEFPITCHER);
        positionMap.put(FANTASYUTILITY.getPositionId(), FANTASYUTILITY);
        positionMap.put(FANTASYCATCHER.getPositionId(), FANTASYCATCHER);
        positionMap.put(FANTASYFIRSTBASE.getPositionId(), FANTASYFIRSTBASE);
        positionMap.put(FANTASYSECONDBASE.getPositionId(), FANTASYSECONDBASE);
        positionMap.put(FANTASYTHIRDBASE.getPositionId(), FANTASYTHIRDBASE);
        positionMap.put(FANTASYSHORTSTOP.getPositionId(), FANTASYSHORTSTOP);
        positionMap.put(FANTASYOUTFIELD.getPositionId(), FANTASYOUTFIELD);
        positionMap.put(FANTASYMIDDLEINFIELD.getPositionId(), FANTASYMIDDLEINFIELD);
        positionMap.put(FANTASYCORNERINFIELD.getPositionId(), FANTASYCORNERINFIELD);
        positionMap.put(FANTASYPITCHER.getPositionId(), FANTASYPITCHER);
    }

    @Id
    private Integer positionId;
    @Column(name="positionName")
    private String positionName;
    @Transient
    private SportType positionType;
    @Transient
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

    public static Map<Integer, Position> getPositionMap() { return positionMap; }

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
