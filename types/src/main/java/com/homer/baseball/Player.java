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
    private Position position;

    public Player() { }

    public Player(ResultSet rs) {
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public void parse(ResultSet rs) {
        try {
            setPlayerId(rs.getLong("player.playerId"));
            setPlayerName(rs.getString("player.playerName"));
            setPosition(Position.get(rs.getInt("player.positionId")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(ResultSet rs, String tableName) { }
}
