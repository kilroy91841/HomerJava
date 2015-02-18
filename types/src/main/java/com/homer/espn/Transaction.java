package com.homer.espn;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/17/15.
 */
public class Transaction {

    public static final Type ADD = new Type(2);
    public static final Type DROP = new Type(3);
    public static final Type TRADE = new Type(4);

    public static class Type {
        public int typeId;

        private Type(int typeId) {
            this.typeId = typeId;
        }
    }

    private String playerName;
    private int teamId;
    private Type move;
    private LocalDateTime time;

    public Transaction(String playerName, int teamId, Type move, LocalDateTime time) {
        this.playerName = playerName;
        this.teamId = teamId;
        this.move = move;
        this.time = time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Type getMove() {
        return move;
    }

    public void setMove(Type move) {
        this.move = move;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "playerName='" + playerName + '\'' +
                ", teamId=" + teamId +
                ", move=" + move +
                ", time=" + time +
                '}';
    }
}
