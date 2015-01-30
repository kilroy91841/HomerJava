package com.homer.baseball;

import com.homer.Parsable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MLB on 1/25/15.
 */
public class Player implements Parsable{

    private long playerId;
    private String playerName;
    private Position primaryPosition;

    public Player() { }

    public Player(ResultSet rs) throws SQLException {
        parse(rs);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Position getPrimaryPosition() {
        return primaryPosition;
    }

    public void setPrimaryPosition(Position primaryPosition) {
        this.primaryPosition = primaryPosition;
    }

    @Override
    public void parse(ResultSet rs) throws SQLException {
        try {
            setPlayerId(rs.getLong("player.playerId"));
            setPlayerName(rs.getString("player.playerName"));
            setPrimaryPosition(Position.get(rs.getInt("player.primaryPositionId")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(ResultSet rs, String tableName) { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerId != player.playerId) return false;
        if (playerName != null ? !playerName.equals(player.playerName) : player.playerName != null) return false;
        if (primaryPosition != null ? !primaryPosition.equals(player.primaryPosition) : player.primaryPosition != null)
            return false;

        return true;
    }
}
