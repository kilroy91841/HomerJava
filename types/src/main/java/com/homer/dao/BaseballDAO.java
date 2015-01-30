package com.homer.dao;

import com.homer.SportType;
import com.homer.baseball.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/26/15.
 */
public class BaseballDAO extends MySQLDAO {

    public BaseballDAO() {
        super();
    }

    public Player getPlayer(String name) {
        Player player = null;
        Connection connection = getConnection();
        try {

            String sql = "select * from PLAYER player " +
                    "inner join POSITION position " +
                    "on position.positionId = player.primaryPositionId " +
                    "where player.playerName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                player = new Player(rs);
                System.out.println(player);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return player;
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<Team>();
        Connection connection = getConnection();
        try {

            String sql = "select * from TEAM team";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Team t = new Team();
                t.parse(rs);
                System.out.println(t);
                teams.add(t);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return teams;
    }

    public List<Player> getPlayersByTeam(int teamId, SportType teamType) {
        return getPlayersByTeam(teamId, teamType, new Date());
    }

    public List<Player> getPlayersByTeam(int teamId, SportType teamType, Date date) {
        List<Player> players = new ArrayList<Player>();
        Connection connection = getConnection();
        try {
            String sql = "select * from PLAYER player, PLAYERTOTEAM playertoteam, TEAM team " +
                    "where player.playerId = playertoteam.playerid " +
                    "and ";
            if(SportType.FANTASY == teamType) {
                sql += "team.teamId = playertoteam.fantasyTeamId " +
                        "and playertoteam.fantasyTeamId = ? ";
            } else {
                sql += "team.teamId = playertoteam.mlbTeamId " +
                        "and playertoteam.mlbTeamId = ? ";
            }
            sql += "and PLAYERTOTEAM.gameDate = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Player player = new Player(rs);
                System.out.println(player);
                players.add(player);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return players;
    }

    public List<DailyPlayer> getPlayerDailies(int playerId) {
        List<DailyPlayer> dailies = new ArrayList<DailyPlayer>();
        Connection connection = getConnection();
        try {
            String sql = "select * from PLAYER player, PLAYERTOTEAM playerToTeam, TEAM fantasyTeam, TEAM mlbTeam, POSITION position " +
                "where player.playerId = ? " +
                "and playerToTeam.playerId = player.playerId " +
                "and fantasyTeam.teamId = playerToTeam.fantasyTeamId " +
                "and mlbTeam.teamId = playerToTeam.mlbTeamId " +
                "and position.positionId = playerToTeam.fantasyPositionId";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playerId);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                DailyPlayer daily = new DailyPlayer(rs);
                dailies.add(daily);
            }

                rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return dailies;
    }

    public DailyTeam getTeamDaily(int teamId, SportType teamType, Date date) {
        DailyTeam team = null;
        Connection connection = getConnection();
        try {
            String teamTableName;
            String sql = "select * from PLAYER player, PLAYERTOTEAM playertoteam, TEAM mlbTeam, TEAM fantasyTeam " +
                    "where player.playerId = playertoteam.playerid " +
                    "and fantasyTeam.teamId = playertoteam.fantasyTeamId " +
                    "and mlbTeam.teamId = playertoteam.mlbTeamId ";
            if(SportType.FANTASY == teamType) {
                teamTableName = "fantasyTeam";
                sql += "and playertoteam.fantasyTeamId = ? ";
            } else {
                teamTableName = "mlbTeam";
                sql += "and playertoteam.mlbTeamId = ? ";
            }
            sql += "and playerToTeam.gameDate = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            try {
                if(rs.first()) {
                    Team dbTeam = new Team(rs, teamTableName);
                    List<DailyPlayer> players = new ArrayList<DailyPlayer>();
                    rs.beforeFirst();
                    while(rs.next()) {
                        DailyPlayer p = new DailyPlayer(rs);
                        players.add(p);
                    }
                    team = new DailyTeam(dbTeam, players);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return team;
    }

    public FantasyRoster getFantasyRoster(int teamId, Date date) {
        Connection connection = getConnection();
        FantasyRoster roster = null;
        try {
            String teamTableName;
            String sql = "select * from PLAYER player, PLAYERTOTEAM playertoteam, TEAM mlbTeam, TEAM fantasyTeam " +
                    "where player.playerId = playertoteam.playerid " +
                    "and fantasyTeam.teamId = playertoteam.fantasyTeamId " +
                    "and mlbTeam.teamId = playertoteam.mlbTeamId " +
                    "and playertoteam.fantasyTeamId = ? ";

            sql += "and playerToTeam.gameDate = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            try {
                if(rs.first()) {
                    Team dbTeam = new Team(rs, "fantasyTeam");
                    List<DailyPlayer> players = new ArrayList<DailyPlayer>();
                    rs.beforeFirst();
                    while(rs.next()) {
                        DailyPlayer p = new DailyPlayer(rs);
                        players.add(p);
                    }
                    roster = new FantasyRoster(dbTeam, players);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return roster;
    }

}
