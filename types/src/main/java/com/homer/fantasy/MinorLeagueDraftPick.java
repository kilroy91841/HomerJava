package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by arigolub on 1/31/15.
 */
@Entity
@Table(name="MINORLEAGUEDRAFTPICK")
public class MinorLeagueDraftPick {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="minorLeagueDraftPickId")
    private long minorLeagueDraftPickId;
    @OneToOne
    @JoinColumn(name="originalTeamId", referencedColumnName="teamId")
    private Team originalTeam;
    @Column(name="season")
    private int season;
    @Column(name="round")
    private int round;
    @OneToOne
    @JoinColumn(name="owningTeamId", referencedColumnName="teamId")
    private Team owningTeam;
    @Column(name="overall")
    private Integer overall;
    @OneToOne
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    private Player player;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="deadline")
    private LocalDateTime deadline;
    @Column(name="skipped")
    private Boolean skipped;

    public MinorLeagueDraftPick() { }

    public MinorLeagueDraftPick(Team originalTeam, int season, int round, Team owningTeam, Integer overall,
                                Player player, LocalDateTime deadline, Boolean skipped) {
        this.originalTeam = originalTeam;
        this.season = season;
        this.round = round;
        this.owningTeam = owningTeam;
        this.overall = overall;
        this.player = player;
        this.deadline = deadline;
        this.skipped = skipped;
    }

    public long getMinorLeagueDraftPickId() {
        return minorLeagueDraftPickId;
    }

    public void setMinorLeagueDraftPickId(long minorLeagueDraftPickId) {
        this.minorLeagueDraftPickId = minorLeagueDraftPickId;
    }

    public Team getOriginalTeam() {
        return originalTeam;
    }

    public void setOriginalTeam(Team originalTeam) {
        this.originalTeam = originalTeam;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Team getOwningTeam() {
        return owningTeam;
    }

    public void setOwningTeam(Team owningTeam) {
        this.owningTeam = owningTeam;
    }

    public Integer getOverall() {
        return overall;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return "MinorLeagueDraftPick{" +
                "originalTeam=" + originalTeam +
                ", season=" + season +
                ", round=" + round +
                ", owningTeam=" + owningTeam +
                ", overall=" + overall +
                ", player=" + player +
                ", deadline=" + deadline +
                ", skipped=" + skipped +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinorLeagueDraftPick that = (MinorLeagueDraftPick) o;

        if (round != that.round) return false;
        if (season != that.season) return false;
        if (!originalTeam.equals(that.originalTeam)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalTeam.hashCode();
        result = 31 * result + season;
        result = 31 * result + round;
        return result;
    }
}
