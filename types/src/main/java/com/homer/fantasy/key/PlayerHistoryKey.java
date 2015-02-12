package com.homer.fantasy.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by arigolub on 2/11/15.
 */
@Embeddable
public class PlayerHistoryKey implements Serializable {

    @Column(name="playerId")
    private long playerId;
    @Column(name="season")
    private int season;

    public PlayerHistoryKey() { }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerHistoryKey that = (PlayerHistoryKey) o;

        if (playerId != that.playerId) return false;
        if (season != that.season) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (playerId ^ (playerId >>> 32));
        result = 31 * result + season;
        return result;
    }
}
