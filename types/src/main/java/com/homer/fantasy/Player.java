package com.homer.fantasy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;

/**
 * Created by MLB on 1/25/15.
 */
@Entity
@Table(name="PLAYER")
public class Player {

    @Id
    @Column(name="playerId")
    private long playerId;
    @Column(name="playerName")
    private String playerName;
    @OneToOne
    @JoinColumn(name="primaryPositionId", referencedColumnName="positionId")
    private Position primaryPosition;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="nameLastFirst")
    private String nameLastFirst;
    @Column(name="mlbPlayerId")
    private long mlbPlayerId;
    @Column(name="espnPlayerId")
    private long espnPlayerId;
    @OneToMany
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @OrderBy(clause = "gameDate desc")
    private List<DailyPlayerInfo> dailyPlayerInfoList;
    @OneToMany
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @OrderBy(clause = "season desc")
    private List<PlayerHistory> playerHistoryList;

    public Player() { }

    public Player(String name) {
        setPlayerName(name);
    }

    public Player(long playerId, String playerName, Position primaryPosition) {
        setPlayerId(playerId);
        setPlayerName(playerName);
        setPrimaryPosition(primaryPosition);
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNameLastFirst() {
        return nameLastFirst;
    }

    public void setNameLastFirst(String nameLastFirst) {
        this.nameLastFirst = nameLastFirst;
    }

    public long getMlbPlayerId() {
        return mlbPlayerId;
    }

    public void setMlbPlayerId(long mlbPlayerId) {
        this.mlbPlayerId = mlbPlayerId;
    }

    public long getEspnPlayerId() {
        return espnPlayerId;
    }

    public void setEspnPlayerId(long espnPlayerId) {
        this.espnPlayerId = espnPlayerId;
    }

    public List<DailyPlayerInfo> getDailyPlayerInfoList() {
        if(dailyPlayerInfoList == null) {
            dailyPlayerInfoList = new ArrayList<DailyPlayerInfo>();
        }
        return dailyPlayerInfoList;
    }

    public void setDailyPlayerInfoList(List<DailyPlayerInfo> dailyPlayerInfoList) {
        this.dailyPlayerInfoList = dailyPlayerInfoList;
    }

    public void addDailyPlayerInfoList(DailyPlayerInfo dailyPlayerInfo) {
        getDailyPlayerInfoList().add(dailyPlayerInfo);
    }

    public List<PlayerHistory> getPlayerHistoryList() {
        return playerHistoryList;
    }

    public void setPlayerHistoryList(List<PlayerHistory> playerHistoryList) {
        this.playerHistoryList = playerHistoryList;
    }

    public Team getCurrentFantasyTeam() throws Exception {
        if(dailyPlayerInfoList.size() == 0) {
            throw new Exception("Player does not have any daily info");
        }
        DailyPlayerInfo latestDailyPlayerInfo = dailyPlayerInfoList.get(0);
        return latestDailyPlayerInfo.getFantasyTeam();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerId != player.playerId) return false;
        if (mlbPlayerId != player.mlbPlayerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (playerId ^ (playerId >>> 32));
        result = 31 * result + (int) (mlbPlayerId ^ (mlbPlayerId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", primaryPosition=" + primaryPosition +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nameLastFirst='" + nameLastFirst + '\'' +
                ", mlbPlayerId=" + mlbPlayerId +
                ", espnPlayerId=" + espnPlayerId +
                ", dailyPlayerInfoList=" + dailyPlayerInfoList +
                ", playerHistoryList=" + playerHistoryList +
                '}';
    }
}
