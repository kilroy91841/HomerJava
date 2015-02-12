package com.homer.fantasy.key;

import com.homer.fantasy.Player;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arigolub on 2/11/15.
 */
@Embeddable
public class PlayerHistoryKey implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Player player;
    @Column(name="season")
    private int season;

    public PlayerHistoryKey() { }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "PlayerHistoryKey{" +
                "playerId=" + player.getPlayerId() +
                ", season=" + season +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerHistoryKey that = (PlayerHistoryKey) o;

        if (season != that.season) return false;
        if (!player.equals(that.player)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + season;
        return result;
    }
}
