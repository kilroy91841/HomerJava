package com.homer.mlb;

import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 * Created by arigolub on 2/1/15.
 */
public class Stats {

    private Integer ab;
    private Integer ao;
    private Double avg;
    private Integer bb;
    private Integer cs;
    private Integer d;
    private String date;
    private DateTime game_date;
    private String game_id;
    private Long game_pk;
    private String game_type;
    private Integer go;
    private Double go_ao;
    private Integer h;
    private Integer h2b;
    private Integer h3b;
    private Integer hbp;
    private String home_away;
    private Integer hr;
    private Integer ibb;
    private Integer lob;
    private Double obp;
    private String opp;
    private Integer opp_score;
    private String opp_team_display_full;
    private String opp_team_display_short;
    private Integer opp_team_id;
    private Double ops;
    private Integer r;
    private Integer rbi;
    private Integer sac;
    private Integer sb;
    private Integer sf;
    private Double slg;
    private Integer so;
    private Integer t;
    private Integer tb;
    private String team_result;
    private Integer team_score;

    public Stats(MLBJSONObject jsonObject) throws Exception {
        this.ab = jsonObject.getInteger("ab");
        this.ao = jsonObject.getInteger("ao");
        this.bb = jsonObject.getInteger("bb");
        this.cs = jsonObject.getInteger("cs");
        this.d = jsonObject.getInteger("d");
        this.go = jsonObject.getInteger("go");
        this.h = jsonObject.getInteger("h");
        this.h2b = jsonObject.getInteger("h2b");
        this.h3b = jsonObject.getInteger("h3b");
        this.hbp = jsonObject.getInteger("hbp");
        this.hr = jsonObject.getInteger("hr");
        this.ibb = jsonObject.getInteger("ibb");
        this.lob = jsonObject.getInteger("lob");
        this.opp_score = jsonObject.getInteger("opp_score");
        this.opp_team_id = jsonObject.getInteger("opp_team_id");
        this.r = jsonObject.getInteger("r");
        this.rbi = jsonObject.getInteger("rbi");
        this.sac = jsonObject.getInteger("sac");
        this.sb = jsonObject.getInteger("sb");
        this.sf = jsonObject.getInteger("sf");
        this.so = jsonObject.getInteger("so");
        this.t = jsonObject.getInteger("t");
        this.tb = jsonObject.getInteger("tb");
        this.team_score = jsonObject.getInteger("team_score");
        this.avg = jsonObject.getDoubleProtected("avg");
        this.go_ao = jsonObject.getDoubleProtected("go_ao");
        this.obp = jsonObject.getDoubleProtected("obp");
        this.ops = jsonObject.getDoubleProtected("ops");
        this.slg = jsonObject.getDoubleProtected("slg");
        this.date = jsonObject.getString("date");
        this.game_date = jsonObject.getDateTime("game_date");
        this.game_id = jsonObject.getString("game_id");
        this.game_type = jsonObject.getString("game_type");
        this.home_away = jsonObject.getString("home_away");
        this.opp = jsonObject.getString("opp");
        this.opp_team_display_full = jsonObject.getString("opp_team_display_full");
        this.opp_team_display_short = jsonObject.getString("opp_team_display_short");
        this.team_result = jsonObject.getString("team_result");
        this.game_pk = jsonObject.getLongProtected("game_pk");
    }

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public Integer getAo() {
        return ao;
    }

    public void setAo(Integer ao) {
        this.ao = ao;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer getBb() {
        return bb;
    }

    public void setBb(Integer bb) {
        this.bb = bb;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateTime getGame_date() {
        return game_date;
    }

    public void setGame_date(DateTime game_date) {
        this.game_date = game_date;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public Long getGame_pk() {
        return game_pk;
    }

    public void setGame_pk(Long game_pk) {
        this.game_pk = game_pk;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public Integer getGo() {
        return go;
    }

    public void setGo(Integer go) {
        this.go = go;
    }

    public Double getGo_ao() {
        return go_ao;
    }

    public void setGo_ao(Double go_ao) {
        this.go_ao = go_ao;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getH2b() {
        return h2b;
    }

    public void setH2b(Integer h2b) {
        this.h2b = h2b;
    }

    public Integer getH3b() {
        return h3b;
    }

    public void setH3b(Integer h3b) {
        this.h3b = h3b;
    }

    public Integer getHbp() {
        return hbp;
    }

    public void setHbp(Integer hbp) {
        this.hbp = hbp;
    }

    public String getHome_away() {
        return home_away;
    }

    public void setHome_away(String home_away) {
        this.home_away = home_away;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public Integer getIbb() {
        return ibb;
    }

    public void setIbb(Integer ibb) {
        this.ibb = ibb;
    }

    public Integer getLob() {
        return lob;
    }

    public void setLob(Integer lob) {
        this.lob = lob;
    }

    public Double getObp() {
        return obp;
    }

    public void setObp(Double obp) {
        this.obp = obp;
    }

    public String getOpp() {
        return opp;
    }

    public void setOpp(String opp) {
        this.opp = opp;
    }

    public Integer getOpp_score() {
        return opp_score;
    }

    public void setOpp_score(Integer opp_score) {
        this.opp_score = opp_score;
    }

    public String getOpp_team_display_full() {
        return opp_team_display_full;
    }

    public void setOpp_team_display_full(String opp_team_display_full) {
        this.opp_team_display_full = opp_team_display_full;
    }

    public String getOpp_team_display_short() {
        return opp_team_display_short;
    }

    public void setOpp_team_display_short(String opp_team_display_short) {
        this.opp_team_display_short = opp_team_display_short;
    }

    public Integer getOpp_team_id() {
        return opp_team_id;
    }

    public void setOpp_team_id(Integer opp_team_id) {
        this.opp_team_id = opp_team_id;
    }

    public Double getOps() {
        return ops;
    }

    public void setOps(Double ops) {
        this.ops = ops;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getRbi() {
        return rbi;
    }

    public void setRbi(Integer rbi) {
        this.rbi = rbi;
    }

    public Integer getSac() {
        return sac;
    }

    public void setSac(Integer sac) {
        this.sac = sac;
    }

    public Integer getSb() {
        return sb;
    }

    public void setSb(Integer sb) {
        this.sb = sb;
    }

    public Integer getSf() {
        return sf;
    }

    public void setSf(Integer sf) {
        this.sf = sf;
    }

    public Double getSlg() {
        return slg;
    }

    public void setSlg(Double slg) {
        this.slg = slg;
    }

    public Integer getSo() {
        return so;
    }

    public void setSo(Integer so) {
        this.so = so;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Integer getTb() {
        return tb;
    }

    public void setTb(Integer tb) {
        this.tb = tb;
    }

    public String getTeam_result() {
        return team_result;
    }

    public void setTeam_result(String team_result) {
        this.team_result = team_result;
    }

    public Integer getTeam_score() {
        return team_score;
    }

    public void setTeam_score(Integer team_score) {
        this.team_score = team_score;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "ab=" + ab +
                ", ao=" + ao +
                ", avg=" + avg +
                ", bb=" + bb +
                ", cs=" + cs +
                ", d=" + d +
                ", date=" + date +
                ", game_date=" + game_date +
                ", game_id='" + game_id + '\'' +
                ", game_pk=" + game_pk +
                ", game_type='" + game_type + '\'' +
                ", go=" + go +
                ", go_ao=" + go_ao +
                ", h=" + h +
                ", h2b=" + h2b +
                ", h3b=" + h3b +
                ", hbp=" + hbp +
                ", home_away='" + home_away + '\'' +
                ", hr=" + hr +
                ", ibb=" + ibb +
                ", lob=" + lob +
                ", obp=" + obp +
                ", opp='" + opp + '\'' +
                ", opp_score=" + opp_score +
                ", opp_team_display_full='" + opp_team_display_full + '\'' +
                ", opp_team_display_short='" + opp_team_display_short + '\'' +
                ", opp_team_id=" + opp_team_id +
                ", ops=" + ops +
                ", r=" + r +
                ", rbi=" + rbi +
                ", sac=" + sac +
                ", sb=" + sb +
                ", sf=" + sf +
                ", slg=" + slg +
                ", so=" + so +
                ", t=" + t +
                ", tb=" + tb +
                ", team_result='" + team_result + '\'' +
                ", team_score=" + team_score +
                '}';
    }
}