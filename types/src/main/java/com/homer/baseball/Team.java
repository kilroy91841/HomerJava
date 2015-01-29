package com.homer.baseball;

import com.homer.Parsable;
import com.homer.SportType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MLB on 1/25/15.
 */
public class Team implements Parsable{

    private static final String DEFAULT_TABLE_NAME = "team";

    private Integer teamId;
    private String teamName;
    private SportType teamType;
    private String teamCode;

    public Team() { }

    public Team(ResultSet rs) {
        parse(rs);
    }

    public Team(ResultSet rs, String tableName) {
        parse(rs, tableName);
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public SportType getTeamType() {
        return teamType;
    }

    public void setTeamType(SportType teamType) {
        this.teamType = teamType;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    @Override
    public void parse(ResultSet rs) {
        parse(rs, DEFAULT_TABLE_NAME);
    }

    @Override
    public void parse(ResultSet rs, String tableName) {
        try {
            setTeamId(rs.getInt(tableName + ".teamId"));
            setTeamName(rs.getString(tableName + ".teamName"));
            setTeamType(SportType.getSportType(rs.getString(tableName + ".teamType")));
            setTeamCode(rs.getString(tableName + ".teamCode"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamType=" + teamType +
                ", teamCode='" + teamCode + '\'' +
                '}';
    }
}
