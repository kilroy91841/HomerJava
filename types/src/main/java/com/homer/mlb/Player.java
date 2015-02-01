package com.homer.mlb;

import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 * Created by arigolub on 2/1/15.
 */
public class Player {

    private String active_sw;
    private Integer age;
    private String bats;
    private DateTime birth_date;
    private Integer jersey_number;
    private String name_display_first_last;
    private String name_display_first_last_html;
    private String name_display_last_first;
    private String name_display_last_first_html;
    private String name_display_roster;
    private String name_display_roster_html;
    private String name_first;
    private String name_full;
    private String name_last;
    private String name_use;
    private Long player_id;
    private Integer primary_position;
    private String primary_position_txt;
    private String primary_stat_type;
    private DateTime pro_debut_date;
    private DateTime start_date;
    private String status;
    private String status_code;
    private DateTime status_date;
    private String team_abbrev;
    private String team_code;
    private Integer team_id;
    private String team_name;
    private String _throws;
    private String twitter_id;

    public Player(MLBJSONObject jsonObject) throws Exception {
        this.active_sw = jsonObject.getString("active_sw");
        this.bats = jsonObject.getString("bats");
        this.name_display_first_last = jsonObject.getString("name_display_first_last");
        this.name_display_first_last_html = jsonObject.getString("name_display_first_last_html");
        this.name_display_last_first = jsonObject.getString("name_display_last_first");
        this.name_display_last_first_html = jsonObject.getString("name_display_last_first_html");
        this.name_display_roster = jsonObject.getString("name_display_roster");
        this.name_display_roster_html = jsonObject.getString("name_display_roster_html");
        this.name_first = jsonObject.getString("name_first");
        this.name_full = jsonObject.getString("name_full");
        this.name_last = jsonObject.getString("name_last");
        this.name_use = jsonObject.getString("name_use");
        this.primary_position_txt = jsonObject.getString("primary_position_txt");
        this.primary_stat_type = jsonObject.getString("primary_stat_type");
        this.status = jsonObject.getString("status");
        this.status_code = jsonObject.getString("status_code");
        this.team_abbrev = jsonObject.getString("team_abbrev");
        this.team_code = jsonObject.getString("team_code");
        this.team_name = jsonObject.getString("team_name");
        this._throws = jsonObject.getString("throws");
        this.twitter_id = jsonObject.getString("twitter_id");
        this.jersey_number = jsonObject.getInteger("jersey_number");
        this.primary_position = jsonObject.getInteger("primary_position");
        this.team_id = jsonObject.getInteger("team_id");
        this.birth_date = jsonObject.getDateTime("birth_date");
        this.pro_debut_date = jsonObject.getDateTime("pro_debut_date");
        this.start_date = jsonObject.getDateTime("start_date");
        this.status_date = jsonObject.getDateTime("status_date");
        this.player_id = jsonObject.getLongProtected("player_id");
        this.age = jsonObject.getInteger("age");
    }

    public String getActive_sw() {
        return active_sw;
    }

    public void setActive_sw(String active_sw) {
        this.active_sw = active_sw;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBats() {
        return bats;
    }

    public void setBats(String bats) {
        this.bats = bats;
    }

    public DateTime getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(DateTime birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getJersey_number() {
        return jersey_number;
    }

    public void setJersey_number(Integer jersey_number) {
        this.jersey_number = jersey_number;
    }

    public String getName_display_first_last() {
        return name_display_first_last;
    }

    public void setName_display_first_last(String name_display_first_last) {
        this.name_display_first_last = name_display_first_last;
    }

    public String getName_display_first_last_html() {
        return name_display_first_last_html;
    }

    public void setName_display_first_last_html(String name_display_first_last_html) {
        this.name_display_first_last_html = name_display_first_last_html;
    }

    public String getName_display_last_first() {
        return name_display_last_first;
    }

    public void setName_display_last_first(String name_display_last_first) {
        this.name_display_last_first = name_display_last_first;
    }

    public String getName_display_last_first_html() {
        return name_display_last_first_html;
    }

    public void setName_display_last_first_html(String name_display_last_first_html) {
        this.name_display_last_first_html = name_display_last_first_html;
    }

    public String getName_display_roster() {
        return name_display_roster;
    }

    public void setName_display_roster(String name_display_roster) {
        this.name_display_roster = name_display_roster;
    }

    public String getName_display_roster_html() {
        return name_display_roster_html;
    }

    public void setName_display_roster_html(String name_display_roster_html) {
        this.name_display_roster_html = name_display_roster_html;
    }

    public String getName_first() {
        return name_first;
    }

    public void setName_first(String name_first) {
        this.name_first = name_first;
    }

    public String getName_full() {
        return name_full;
    }

    public void setName_full(String name_full) {
        this.name_full = name_full;
    }

    public String getName_last() {
        return name_last;
    }

    public void setName_last(String name_last) {
        this.name_last = name_last;
    }

    public String getName_use() {
        return name_use;
    }

    public void setName_use(String name_use) {
        this.name_use = name_use;
    }

    public Long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Long player_id) {
        this.player_id = player_id;
    }

    public Integer getPrimary_position() {
        return primary_position;
    }

    public void setPrimary_position(Integer primary_position) {
        this.primary_position = primary_position;
    }

    public String getPrimary_position_txt() {
        return primary_position_txt;
    }

    public void setPrimary_position_txt(String primary_position_txt) {
        this.primary_position_txt = primary_position_txt;
    }

    public String getPrimary_stat_type() {
        return primary_stat_type;
    }

    public void setPrimary_stat_type(String primary_stat_type) {
        this.primary_stat_type = primary_stat_type;
    }

    public DateTime getPro_debut_date() {
        return pro_debut_date;
    }

    public void setPro_debut_date(DateTime pro_debut_date) {
        this.pro_debut_date = pro_debut_date;
    }

    public DateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(DateTime start_date) {
        this.start_date = start_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public DateTime getStatus_date() {
        return status_date;
    }

    public void setStatus_date(DateTime status_date) {
        this.status_date = status_date;
    }

    public String getTeam_abbrev() {
        return team_abbrev;
    }

    public void setTeam_abbrev(String team_abbrev) {
        this.team_abbrev = team_abbrev;
    }

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getThrows() {
        return _throws;
    }

    public void setThrows(String _throws) {
        this._throws = _throws;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "active_sw='" + active_sw + '\'' +
                ", age=" + age +
                ", bats='" + bats + '\'' +
                ", birth_date=" + birth_date +
                ", jersey_number=" + jersey_number +
                ", name_display_first_last='" + name_display_first_last + '\'' +
                ", name_display_first_last_html='" + name_display_first_last_html + '\'' +
                ", name_display_last_first='" + name_display_last_first + '\'' +
                ", name_display_last_first_html='" + name_display_last_first_html + '\'' +
                ", name_display_roster='" + name_display_roster + '\'' +
                ", name_display_roster_html='" + name_display_roster_html + '\'' +
                ", name_first='" + name_first + '\'' +
                ", name_full='" + name_full + '\'' +
                ", name_last='" + name_last + '\'' +
                ", name_use='" + name_use + '\'' +
                ", player_id=" + player_id +
                ", primary_position=" + primary_position +
                ", primary_position_txt='" + primary_position_txt + '\'' +
                ", primary_stat_type='" + primary_stat_type + '\'' +
                ", pro_debut_date=" + pro_debut_date +
                ", start_date=" + start_date +
                ", status='" + status + '\'' +
                ", status_code='" + status_code + '\'' +
                ", status_date=" + status_date +
                ", team_abbrev='" + team_abbrev + '\'' +
                ", team_code='" + team_code + '\'' +
                ", team_id=" + team_id +
                ", team_name='" + team_name + '\'' +
                ", _throws='" + _throws + '\'' +
                ", twitter_id='" + twitter_id + '\'' +
                '}';
    }
}
