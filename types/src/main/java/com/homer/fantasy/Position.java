package com.homer.fantasy;

import com.homer.PlayerStatus;
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

    public static final Position STARTINGPITCHER = new Position(1, "STARTINGPITCHER", SportType.MLB, "P");
    public static final Position CATCHER = new Position(2, "CATCHER", SportType.MLB, "C");
    public static final Position FIRSTBASE = new Position(3, "FIRSTBASE", SportType.MLB, "1B");
    public static final Position SECONDBASE = new Position(4, "SECONDBASE", SportType.MLB, "2B");
    public static final Position THIRDBASE = new Position(5, "THIRDBASE", SportType.MLB, "3B");
    public static final Position SHORTSTOP = new Position(6, "SHORTSTOP", SportType.MLB, "SS");
    public static final Position LEFTFIELD = new Position(7, "LEFTFIELD", SportType.MLB, "LF");
    public static final Position CENTERFIELD = new Position(8, "CENTERFIELD", SportType.MLB, "CF");
    public static final Position RIGHTFIELD = new Position(9, "RIGHTFIELD", SportType.MLB, "RF");
    public static final Position DESIGNATEDHITTER = new Position(10, "DESIGNATEDHITTER", SportType.MLB, "DH");
    public static final Position RELIEFPITCHER = new Position(11, "RELIEFPITCHER", SportType.MLB, "RP");
    public static final Position FANTASYPITCHER =  new Position(101, "PITCHER", SportType.FANTASY, "P");
    public static final Position FANTASYCATCHER =  new Position(102, "CATCHER", SportType.FANTASY, "C");
    public static final Position FANTASYFIRSTBASE =  new Position(103, "FIRSTBASE", SportType.FANTASY, "1B");
    public static final Position FANTASYSECONDBASE =  new Position(104, "SECONDBASE", SportType.FANTASY, "2B");
    public static final Position FANTASYTHIRDBASE =  new Position(105, "THIRDBASE", SportType.FANTASY, "3B");
    public static final Position FANTASYSHORTSTOP =  new Position(106, "SHORTSTOP", SportType.FANTASY, "SS");
    public static final Position FANTASYOUTFIELD =  new Position(107, "OUTFIELD", SportType.FANTASY, "OF");
    public static final Position FANTASYMIDDLEINFIELD =  new Position(108, "MIDDLEINFIELD", SportType.FANTASY, "2B/SS");
    public static final Position FANTASYCORNERINFIELD =  new Position(109, "CORNERINFIELD", SportType.FANTASY, "1B/3B");
    public static final Position FANTASYUTILITY =  new Position(110, "UTILITY", SportType.FANTASY, "UTIL");
    public static final Position FANTASYBENCH =  new Position(111, "BENCH", SportType.FANTASY, "Bench");
    public static final Position FANTASYDISABLEDLIST =  new Position(112, "DISABLEDLIST", SportType.FANTASY, "DL");

    private static Map<Integer, Position> positionMap = new HashMap<Integer, Position>();
    private static Map<String, Position> positionCodeMap = new HashMap<String, Position>();
    private static Map<Position, PlayerStatus> fantasyPositionToStatusMap = new HashMap<Position, PlayerStatus>();

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
        positionMap.put(FANTASYBENCH.getPositionId(), FANTASYBENCH);
        positionMap.put(FANTASYDISABLEDLIST.getPositionId(), FANTASYDISABLEDLIST);

        positionCodeMap.put(STARTINGPITCHER.getPositionCode(), STARTINGPITCHER);
        positionCodeMap.put(CATCHER.getPositionCode(), CATCHER);
        positionCodeMap.put(FIRSTBASE.getPositionCode(), FIRSTBASE);
        positionCodeMap.put(SECONDBASE.getPositionCode(), SECONDBASE);
        positionCodeMap.put(THIRDBASE.getPositionCode(), THIRDBASE);
        positionCodeMap.put(SHORTSTOP.getPositionCode(), SHORTSTOP);
        positionCodeMap.put(LEFTFIELD.getPositionCode(), LEFTFIELD);
        positionCodeMap.put(CENTERFIELD.getPositionCode(), CENTERFIELD);
        positionCodeMap.put(RIGHTFIELD.getPositionCode(), RIGHTFIELD);
        positionCodeMap.put(DESIGNATEDHITTER.getPositionCode(), DESIGNATEDHITTER);
        positionCodeMap.put(RELIEFPITCHER.getPositionCode(), RELIEFPITCHER);
        positionCodeMap.put(FANTASYUTILITY.getPositionCode(), FANTASYUTILITY);
        positionCodeMap.put(FANTASYCATCHER.getPositionCode(), FANTASYCATCHER);
        positionCodeMap.put(FANTASYFIRSTBASE.getPositionCode(), FANTASYFIRSTBASE);
        positionCodeMap.put(FANTASYSECONDBASE.getPositionCode(), FANTASYSECONDBASE);
        positionCodeMap.put(FANTASYTHIRDBASE.getPositionCode(), FANTASYTHIRDBASE);
        positionCodeMap.put(FANTASYSHORTSTOP.getPositionCode(), FANTASYSHORTSTOP);
        positionCodeMap.put(FANTASYOUTFIELD.getPositionCode(), FANTASYOUTFIELD);
        positionCodeMap.put(FANTASYMIDDLEINFIELD.getPositionCode(), FANTASYMIDDLEINFIELD);
        positionCodeMap.put(FANTASYCORNERINFIELD.getPositionCode(), FANTASYCORNERINFIELD);
        positionCodeMap.put(FANTASYPITCHER.getPositionCode(), FANTASYPITCHER);
        positionCodeMap.put(FANTASYBENCH.getPositionCode(), FANTASYBENCH);
        positionCodeMap.put(FANTASYDISABLEDLIST.getPositionCode(), FANTASYDISABLEDLIST);

        fantasyPositionToStatusMap.put(FANTASYUTILITY, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYCATCHER, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYFIRSTBASE, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYSECONDBASE, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYTHIRDBASE, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYSHORTSTOP, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYOUTFIELD, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYMIDDLEINFIELD, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYCORNERINFIELD, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYPITCHER, PlayerStatus.ACTIVE);
        fantasyPositionToStatusMap.put(FANTASYBENCH, PlayerStatus.INACTIVE);
        fantasyPositionToStatusMap.put(FANTASYDISABLEDLIST, PlayerStatus.DISABLEDLIST);
    }

    @Id
    private Integer positionId;
    @Column(name="positionName")
    private String positionName;
    @Enumerated(EnumType.STRING)
    private SportType positionType;
    @Column(name="positionCode")
    private String positionCode;

    public Position() { }

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

    public static Position get(String code) { return positionCodeMap.get(code); }

    public static PlayerStatus getStatusFromPosition(Position position) {
        return fantasyPositionToStatusMap.get(position);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!positionId.equals(position.positionId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return positionId.hashCode();
    }
}
