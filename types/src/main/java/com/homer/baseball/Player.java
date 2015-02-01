package com.homer.baseball;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLB on 1/25/15.
 */
public class Player implements Tradable {

    private long playerId;
    private String playerName;
    private Position primaryPosition;
    private List<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList;

    public Player() { }

    public Player(long playerId, String playerName, Position primaryPosition) {
        setPlayerId(playerId);
        setPlayerName(playerName);
        setPrimaryPosition(primaryPosition);
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

    public List<ThirdPartyPlayerInfo> getThirdPartyPlayerInfoList() {
        if(thirdPartyPlayerInfoList == null) {
            thirdPartyPlayerInfoList = new ArrayList<ThirdPartyPlayerInfo>();
        }
        return thirdPartyPlayerInfoList;
    }

    public void setThirdPartyPlayerInfoList(List<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList) {
        this.thirdPartyPlayerInfoList = thirdPartyPlayerInfoList;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", primaryPosition=" + primaryPosition +
                ", thirdPartyPlayerInfoList=" + thirdPartyPlayerInfoList +
                '}';
    }

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
