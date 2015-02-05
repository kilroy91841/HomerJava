package com.homer.fantasy;

import com.homer.exception.StatusNotFoundException;

import java.util.Date;

/**
 * Created by arigolub on 2/1/15.
 */
public class Vulture {

    public static final VultureStatus ACTIVE = new VultureStatus("ACTIVE");
    public static final VultureStatus RESOLVED = new VultureStatus("RESOLVED");
    public static final VultureStatus GRANTED = new VultureStatus("GRANTED");

    public Team vulturingTeam;
    public Team offendingTeam;
    public Player player;
    public Date deadline;
    public VultureStatus status;

    public Vulture() { }

    public Vulture(Team vulturingTeam, Team offendingTeam, Player player, Date deadline, VultureStatus status) {
        this.vulturingTeam = vulturingTeam;
        this.offendingTeam = offendingTeam;
        this.player = player;
        this.deadline = deadline;
        this.status = status;
    }

    public Team getVulturingTeam() {
        return vulturingTeam;
    }

    public void setVulturingTeam(Team vulturingTeam) {
        this.vulturingTeam = vulturingTeam;
    }

    public Team getOffendingTeam() {
        return offendingTeam;
    }

    public void setOffendingTeam(Team offendingTeam) {
        this.offendingTeam = offendingTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public VultureStatus getStatus() {
        return status;
    }

    public void setStatus(VultureStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vulture{" +
                "vulturingTeam=" + vulturingTeam +
                ", offendingTeam=" + offendingTeam +
                ", player=" + player +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vulture vulture = (Vulture) o;

        if (!deadline.equals(vulture.deadline)) return false;
        if (!offendingTeam.equals(vulture.offendingTeam)) return false;
        if (!player.equals(vulture.player)) return false;
        if (!status.equals(vulture.status)) return false;
        if (!vulturingTeam.equals(vulture.vulturingTeam)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vulturingTeam.hashCode();
        result = 31 * result + offendingTeam.hashCode();
        result = 31 * result + player.hashCode();
        result = 31 * result + deadline.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    public static class VultureStatus {

        private String name;

        private VultureStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static VultureStatus get(String vultureStatusName) throws StatusNotFoundException {
            if(ACTIVE.getName().equals(vultureStatusName)) {
                return ACTIVE;
            } else if(RESOLVED.getName().equals(vultureStatusName)) {
                return RESOLVED;
            } else if(GRANTED.getName().equals(vultureStatusName)) {
                return GRANTED;
            }
            throw new StatusNotFoundException("Unrecognized vulture status name: " + vultureStatusName);
        }
    }
}
