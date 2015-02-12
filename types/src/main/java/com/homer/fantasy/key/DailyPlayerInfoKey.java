package com.homer.fantasy.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by arigolub on 2/11/15.
 */
@Embeddable
public class DailyPlayerInfoKey implements Serializable {
    @Column(name="playerId")
    private long playerId;
    @Temporal(TemporalType.DATE)
    @Column(name="gameDate")
    private Date date;

    public DailyPlayerInfoKey() { }

    public long getPlayerId() { return playerId; }
    public void setPlayerId(long playerId) { this.playerId = playerId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyPlayerInfoKey that = (DailyPlayerInfoKey) o;

        if (playerId != that.playerId) return false;
        if (!date.equals(that.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (playerId ^ (playerId >>> 32));
        result = 31 * result + date.hashCode();
        return result;
    }
}
