package com.homer.espn;

import com.homer.util.ESPNTransactionTypePersistenceConverter;
import com.homer.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/17/15.
 */
@Entity
@Table(name="ESPNTRANSACTION")
public class Transaction {

    public static final Type ADD = new Type(2, "ADD");
    public static final Type DROP = new Type(3, "DROP");
    public static final Type TRADE = new Type(4, "TRADE_ACQ");

    public static Type getTypeByName(String name) {
        switch(name) {
            case "ADD":
                return ADD;
            case "DROP":
                return DROP;
            case "TRADE" :
                return TRADE;
            default :
                return null;
        }
    }

    public static class Type {
        private int typeId;
        private String typeName;

        private Type(int typeId, String typeName) {
            this.typeId = typeId;
            this.typeName = typeName;
        }

        public int getTypeId() {
            return typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        @Override
        public String toString() {
            return "Type{" +
                    "typeId=" + typeId +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Type type = (Type) o;

            if (typeId != type.typeId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return typeId;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Column
    private String playerName;
    @Column
    private int teamId;
    @Column
    @Convert(converter= ESPNTransactionTypePersistenceConverter.class)
    private Type move;
    @Column
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    private LocalDateTime time;
    @Column
    private String nodeText;
    @Transient
    private boolean alreadySeen;

    public Transaction() { }

    public Transaction(String nodeText, LocalDateTime time) {
        this.nodeText = nodeText;
        this.time = time;
    }

    public Transaction(String playerName, int teamId, Type move, LocalDateTime time, String nodeText) {
        this.playerName = playerName;
        this.teamId = teamId;
        this.move = move;
        this.time = time;
        this.nodeText = nodeText;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
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

    public String getNodeText() {
        return nodeText;
    }

    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    public boolean isAlreadySeen() {
        return alreadySeen;
    }

    public void setAlreadySeen(boolean alreadySeen) {
        this.alreadySeen = alreadySeen;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "playerName='" + playerName + '\'' +
                ", teamId=" + teamId +
                ", move=" + move +
                ", time=" + time +
                ", nodeText=" + nodeText +
                ", alreadySeen=" + alreadySeen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!nodeText.equals(that.nodeText)) return false;
        if (!time.equals(that.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nodeText != null ? nodeText.hashCode() : 0;
    }
}
