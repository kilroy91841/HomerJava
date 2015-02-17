package com.homer.espn;

import com.homer.fantasy.Position;

/**
 * Created by arigolub on 2/16/15.
 */
public class Player {

    private String playerName;
    private Long playerId;
    private int teamId;
    private Position position;

    public Player() { }

    public Player(String playerName, Long playerId, int teamId, Position position) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.teamId = teamId;
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
                "playerName='" + playerName + '\'' +
                ", playerId=" + playerId +
                ", teamId=" + teamId +
                ", position=" + position +
                '}';
    }
}
