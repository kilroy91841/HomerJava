package com.homer.baseball;

import com.homer.Parsable;
import com.homer.SportType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MLB on 1/25/15.
 */
public class Team implements Parsable{

    private Integer teamId;
    private String teamName;
    private SportType teamType;
    private String teamCode;

    public Team() { }

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
        try {
            setTeamId(rs.getInt("team.teamId"));
            setTeamName(rs.getString("team.teamName"));
            setTeamType(SportType.getSportType(rs.getString("team.teamType")));
            setTeamCode(rs.getString("team.teamCode"));
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
