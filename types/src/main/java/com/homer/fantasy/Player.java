package com.homer.fantasy;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.*;
import org.hibernate.annotations.OrderBy;

/**
 * Created by MLB on 1/25/15.
 */
@Entity
@Table(name="PLAYER")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="playerId")
    private Long playerId;
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
    private Long mlbPlayerId;
    @Column(name="espnPlayerId")
    private Long espnPlayerId;
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @OrderBy(clause = "gameDate desc")
    @Fetch(FetchMode.SELECT)
    private List<DailyPlayerInfo> dailyPlayerInfoList;
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @OrderBy(clause = "season desc")
    @Fetch(FetchMode.SELECT)
    private List<PlayerHistory> playerHistoryList;

    public Player() { }
    
    public Player(com.homer.mlb.Player mlbPlayer) {
        setPlayerName(mlbPlayer.getName_display_first_last());
        setFirstName(mlbPlayer.getName_first());
        setLastName(mlbPlayer.getName_last());
        setNameLastFirst(mlbPlayer.getName_display_last_first());
        setPrimaryPosition(Position.get(mlbPlayer.getPrimary_position()));
        setMlbPlayerId(mlbPlayer.getPlayer_id());
    }

    public Player(long playerId) {
        setPlayerId(playerId);
    }

    public Long getPlayerId() {
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

    public Long getMlbPlayerId() {
        return mlbPlayerId;
    }

    public void setMlbPlayerId(Long mlbPlayerId) {
        this.mlbPlayerId = mlbPlayerId;
    }

    public Long getEspnPlayerId() {
        return espnPlayerId;
    }

    public void setEspnPlayerId(Long espnPlayerId) {
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

    public void addDailyPlayerInfo(DailyPlayerInfo dailyPlayerInfo) {
        getDailyPlayerInfoList().add(dailyPlayerInfo);
    }

    public List<PlayerHistory> getPlayerHistoryList() {
        if(playerHistoryList == null) {
            playerHistoryList = new ArrayList<PlayerHistory>();
        }
        return playerHistoryList;
    }

    public void setPlayerHistoryList(List<PlayerHistory> playerHistoryList) {
        this.playerHistoryList = playerHistoryList;
    }

    public void addPlayerHistory(PlayerHistory playerHistory) {
        getPlayerHistoryList().add(playerHistory);
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

        if (mlbPlayerId != null ? !mlbPlayerId.equals(player.mlbPlayerId) : player.mlbPlayerId != null) return false;
        if (playerId != null ? !playerId.equals(player.playerId) : player.playerId != null) return false;

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
