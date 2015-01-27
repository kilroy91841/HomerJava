package com.homer.dao;

import com.homer.SportType;
import com.homer.baseball.Player; 
import com.homer.baseball.Team;

import java.sql.*;
import java.util.ArrayList;
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
                    "on position.positionId = player.positionId " +
                    "where player.playerName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                player = new Player();
                player.parse(rs);
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

    public List<Player> getPlayersByTeam(int teamId, SportType teamType, Date date) {
        List<Player> players = new ArrayList<Player>();
        Connection connection = getConnection();
        try {
            String sql = "select * from PLAYER player " +
                    "inner join PLAYERTOTEAM playertoteam " +
                    "on player.playerId = playertoteam.playerid " +
                    "inner join TEAM team ";
            if(SportType.FANTASY == teamType) {
                sql += "on team.teamId = playertoteam.fantasyTeamId " +
                        "where playertoteam.fantasyTeamId = ?";
            } else {
                sql += "on team.teamId = playertoteam.mlbTeamId " +
                        "where playertoteam.mlbTeamId = ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Player player = new Player();
                player.parse(rs);
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
}
