package com.homer.fantasy.mongo.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/13/15.
 */
public class MongoPlayer {

    private int player_id;
    private int espn_player_id;
    private String espn_player_name;
    private String name_display_first_last;
    private String name_first;
    private String name_last;
    private int primary_position;
    private List<MongoPlayerHistory> mongoPlayerHistoryList;

    public MongoPlayer() {
        mongoPlayerHistoryList = new ArrayList<MongoPlayerHistory>();
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getEspn_player_id() {
        return espn_player_id;
    }

    public void setEspn_player_id(int espn_player_id) {
        this.espn_player_id = espn_player_id;
    }

    public String getEspn_player_name() {
        return espn_player_name;
    }

    public void setEspn_player_name(String espn_player_name) {
        this.espn_player_name = espn_player_name;
    }

    public String getName_display_first_last() {
        return name_display_first_last;
    }

    public void setName_display_first_last(String name_display_first_last) {
        this.name_display_first_last = name_display_first_last;
    }

    public String getName_first() {
        return name_first;
    }

    public void setName_first(String name_first) {
        this.name_first = name_first;
    }

    public String getName_last() {
        return name_last;
    }

    public void setName_last(String name_last) {
        this.name_last = name_last;
    }

    public int getPrimary_position() {
        return primary_position;
    }

    public void setPrimary_position(int primary_position) {
        this.primary_position = primary_position;
    }

    public List<MongoPlayerHistory> getMongoPlayerHistoryList() {
        return mongoPlayerHistoryList;
    }

    public void setMongoPlayerHistoryList(List<MongoPlayerHistory> mongoPlayerHistoryList) {
        this.mongoPlayerHistoryList = mongoPlayerHistoryList;
    }
}
