package com.homer.baseball;

/**
 * Created by MLB on 1/25/15.
 */
public class Player {

    private long id;
    private String playerName;
    private Position primaryPosition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", primaryPosition=" + primaryPosition +
                '}';
    }
}
