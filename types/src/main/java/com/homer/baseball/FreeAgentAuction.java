package com.homer.baseball;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arigolub on 2/1/15.
 */
public class FreeAgentAuction {

    public static final Status REQUESTED = new Status("REQUESTED");
    public static final Status ACTIVE = new Status("ACTIVE");
    public static final Status DENIED = new Status("DENIED");
    public static final Status CANCELLED = new Status("CANCELLED");
    public static final Status COMPLETE = new Status("COMPLETE");

    static {
        Status.map.put(REQUESTED.getName(), REQUESTED);
        Status.map.put(ACTIVE.getName(), ACTIVE);
        Status.map.put(DENIED.getName(), DENIED);
        Status.map.put(CANCELLED.getName(), CANCELLED);
        Status.map.put(COMPLETE.getName(), COMPLETE);
    }

    private Team requestingTeam;
    private Player player;
    private Date createdDate;
    private Date modifiedDate;
    private Date deadline;
    private Status status;

    public FreeAgentAuction() { }

    public FreeAgentAuction(Team requestingTeam, Player player, Date createdDate, Date modifiedDate, Date deadline, Status status) {
        this.requestingTeam = requestingTeam;
        this.player = player;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deadline = deadline;
        this.status = status;
    }

    public Team getRequestingTeam() {
        return requestingTeam;
    }

    public void setRequestingTeam(Team requestingTeam) {
        this.requestingTeam = requestingTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FreeAgentAuction{" +
                "requestingTeam=" + requestingTeam +
                ", player=" + player +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeAgentAuction that = (FreeAgentAuction) o;

        if (!createdDate.equals(that.createdDate)) return false;
        if (!deadline.equals(that.deadline)) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;
        if (!player.equals(that.player)) return false;
        if (!requestingTeam.equals(that.requestingTeam)) return false;
        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestingTeam.hashCode();
        result = 31 * result + player.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + deadline.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    public static class Status {
        protected static final Map<String, Status> map = new HashMap<String, Status>();
        private String name;
        private Status(String name) {
            this.name = name;
        }
        public String getName() { return name; }
        public static Status get(String name) throws Exception {
            Status status = map.get(name);
            if(map == null) {
                throw new Exception("Status not found for name: " + name);
            }
            return status;
        }
    }
}
