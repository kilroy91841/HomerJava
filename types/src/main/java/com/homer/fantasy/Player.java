package com.homer.fantasy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MLB on 1/25/15.
 */
public class Player implements Tradable {

    private long playerId;
    private String playerName;
    private Position primaryPosition;
    private String firstName;
    private String lastName;
    private String nameLastFirst;
    private Set<ThirdPartyPlayerInfo> thirdPartyPlayerInfoSet;
    private List<DailyPlayerInfo> dailyPlayerInfoList;

    public Player() { }

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

    public Set<ThirdPartyPlayerInfo> getThirdPartyPlayerInfoSet() {
        if(thirdPartyPlayerInfoSet == null) {
            thirdPartyPlayerInfoSet = new HashSet<ThirdPartyPlayerInfo>();
        }
        return thirdPartyPlayerInfoSet;
    }

    public void setThirdPartyPlayerInfoSet(Set<ThirdPartyPlayerInfo> thirdPartyPlayerInfoSet) {
        this.thirdPartyPlayerInfoSet = thirdPartyPlayerInfoSet;
    }

    public ThirdPartyPlayerInfo getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ThirdPartyProvider provider) {
        for(ThirdPartyPlayerInfo info : getThirdPartyPlayerInfoSet()) {
            if(info.getProvider().equals(provider)) {
                return info;
            }
        }
        return null;
    }

    public void addThirdPartyPlayerInfo(ThirdPartyPlayerInfo info) {
        getThirdPartyPlayerInfoSet().add(info);
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

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", primaryPosition=" + primaryPosition +
                ", thirdPartyPlayerInfoSet=" + thirdPartyPlayerInfoSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerName != null ? !playerName.equals(player.playerName) : player.playerName != null) return false;
        if (primaryPosition != null ? !primaryPosition.equals(player.primaryPosition) : player.primaryPosition != null)
            return false;
        if (getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null ?
                !getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).equals(player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB)) :
                player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null
                ) return false;

        return true;
    }


    public static Player create(ResultSet rs, String tableName) throws SQLException {
        Long playerId = rs.getLong(tableName + ".playerId");
        if(rs.wasNull()) {
            return null;
        }
        Player player = new Player(
                playerId,
                rs.getString(tableName + ".playerName"),
                Position.get(rs.getInt(tableName + ".primaryPositionId"))
        );

        Set<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList = new HashSet<ThirdPartyPlayerInfo>();
        Long mlbPlayerId = rs.getLong(tableName + ".mlbPlayerId");
        if(!rs.wasNull()) {
            thirdPartyPlayerInfoList.add(new ThirdPartyPlayerInfo(mlbPlayerId, ThirdPartyPlayerInfo.MLB));
        }
        player.setThirdPartyPlayerInfoSet(thirdPartyPlayerInfoList);

        return player;
    }
}
