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

    public Team(Integer teamId, String teamName, SportType teamType, String teamCode) {
        setTeamId(teamId);
        setTeamName(teamName);
        setTeamType(teamType);
        setTeamCode(teamCode);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (teamCode != null ? !teamCode.equals(team.teamCode) : team.teamCode != null) return false;
        if (teamId != null ? !teamId.equals(team.teamId) : team.teamId != null) return false;
        if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null) return false;
        if (teamType != null ? !teamType.equals(team.teamType) : team.teamType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teamId != null ? teamId.hashCode() : 0;
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        result = 31 * result + (teamType != null ? teamType.hashCode() : 0);
        result = 31 * result + (teamCode != null ? teamCode.hashCode() : 0);
        return result;
    }
}
