package com.homer.fantasy.key;

import com.homer.fantasy.Player;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by arigolub on 2/11/15.
 */
@Embeddable
public class DailyPlayerInfoKey implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Player player;
    @Temporal(TemporalType.DATE)
    @Column(name="gameDate")
    private Date date;

    public DailyPlayerInfoKey() { }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString() {
        return "DailyPlayerInfoKey{" +
                "playerId=" + player.getPlayerId() +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyPlayerInfoKey that = (DailyPlayerInfoKey) o;

        if (!date.equals(that.date)) return false;
        if (!player.equals(that.player)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
