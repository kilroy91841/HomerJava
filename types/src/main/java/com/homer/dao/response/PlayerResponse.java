package com.homer.dao.response;

import com.homer.fantasy.Player;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerResponse {

    public static final int SUCCESS = 0;
    public static final int DATA_NOT_FOUND = -1;
    public static final int DATA_ALREADY_EXISTS = -2;
    public static final int ERROR = -3;
    public static final int RUNTIME_EXCEPTION = -100;

    private int status;
    private Player player;
    private String errorMesage;
    private Exception exception;

    public PlayerResponse() { }

    public PlayerResponse(int status, Player player, String errorMesage, Exception exception) {
        this.status = status;
        this.player = player;
        this.errorMesage = errorMesage;
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "status=" + status +
                ", player=" + player +
                ", errorMesage='" + errorMesage + '\'' +
                ", exception=" + exception +
                '}';
    }
}
