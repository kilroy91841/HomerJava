package com.homer.scripts;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.mongo.types.MongoPlayer;
import com.homer.fantasy.mongo.types.MongoPlayerHistory;
import com.mongodb.*;
import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
* Created by arigolub on 2/12/15.
*/
public class PlayerHistory {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerHistory.class);

    public static void main(String[] args) {
        HomerDAO dao = new HomerDAO();
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("app18596138");
            DBCollection table = db.getCollection("mlbplayers");
            DBCursor cursor = table.find();
            DBObject o;
            String playerName = null;
            while(cursor.hasNext()) {
                try {
                    o = cursor.next();
                    playerName = (String)o.get("name_display_first_last");
                    MongoPlayer mongoPlayer = new MongoPlayer();

                    try {
                        if (o.get("player_id") != null) mongoPlayer.setPlayer_id((int) o.get("player_id"));
                    } catch(ClassCastException e) {
                        mongoPlayer.setPlayer_id((int)(double)o.get("player_id"));
                    }
                    mongoPlayer.setName_display_first_last(playerName);
                    mongoPlayer.setName_first((String) o.get("name_first"));
                    mongoPlayer.setName_last((String) o.get("name_last"));
                    try {
                        if (o.get("primary_position") != null)
                            mongoPlayer.setPrimary_position(new Integer((String) o.get("primary_position")));
                    } catch(NumberFormatException e) {
                        String primary_position = (String)o.get("primary_position");
                        if("D".equals(primary_position)) mongoPlayer.setPrimary_position(10);
                        if("O".equals(primary_position)) mongoPlayer.setPrimary_position(7);
                    }

                    BasicDBList histories = (BasicDBList) o.get("history");
                    for(int i = 0; i < histories.size(); i++) {
                        BasicDBObject h = (BasicDBObject) histories.get(i);
                        MongoPlayerHistory history = new MongoPlayerHistory();
                        try {
                            if(h.get("contract_year") != null) history.setContract_year((int) h.get("contract_year"));
                        } catch(ClassCastException e) {
                            history.setContract_year((int)(double)h.get("contract_year"));
                        }
                        try {
                            if (h.get("draft_team") != null) history.setDraft_team((int) h.get("draft_team"));
                        } catch(ClassCastException e) {
                            history.setDraft_team((int)(double)h.get("draft_team"));
                        }
                        try {
                            if (h.get("fantasy_team") != null) history.setFantasy_team((int) h.get("fantasy_team"));
                        } catch(ClassCastException e) {
                            history.setFantasy_team((int)(double)h.get("fantasy_team"));
                        }
                        try {
                            if (h.get("keeper_team") != null) history.setKeeper_team((int) h.get("keeper_team"));
                        } catch(ClassCastException e) {
                            try {
                                history.setKeeper_team((new Integer((String) h.get("keeper_team"))));
                            } catch(ClassCastException e1) {
                                history.setKeeper_team((int)(double)h.get("keeper_team"));
                            }
                        }
                        if(h.get("locked_up") != null) history.setLocked_up((boolean) h.get("locked_up"));
                        if(h.get("minor_leaguer") != null) history.setMinor_leaguer((boolean) h.get("minor_leaguer"));
                        try {
                            if (h.get("salary") != null) history.setSalary((int) (double) h.get("salary"));
                        } catch(ClassCastException e) {
                            history.setSalary((int)h.get("salary"));
                        }
                        if(h.get("year") != null) history.setYear((int) h.get("year"));
                        mongoPlayer.getMongoPlayerHistoryList().add(history);
                    }

                    Player player = null;
                    try {
                        player = dao.findPlayerByName(mongoPlayer.getName_display_first_last());
                    } catch(NonUniqueResultException e) {
                        player = dao.findPlayerByMLBPlayerId(mongoPlayer.getPlayer_id());
                    }
                    if(player != null) {
                        if(player.getMlbPlayerId() == mongoPlayer.getPlayer_id()) {
                            for(MongoPlayerHistory history : mongoPlayer.getMongoPlayerHistoryList()) {
                                com.homer.fantasy.PlayerHistory fantasyHistory = new com.homer.fantasy.PlayerHistory();
                                fantasyHistory.setSalary(history.getSalary());
                                fantasyHistory.setSeason(history.getYear());
                                fantasyHistory.setPlayer(player);
                                fantasyHistory.setMinorLeaguer(history.isMinor_leaguer());
                                Team draftTeam = new Team();
                                draftTeam.setTeamId(history.getDraft_team());
                                fantasyHistory.setDraftTeam(draftTeam);
                                fantasyHistory.setKeeperSeason(history.getContract_year());
                                Team keeperTeam = new Team();
                                keeperTeam.setTeamId(history.getKeeper_team());
                                fantasyHistory.setKeeperTeam(keeperTeam);
                                //fantasyHistory.setRookieStatus(history.is);
                                player.getPlayerHistoryList().add(fantasyHistory);
                            }
                            //dao.saveOrUpdate(player);
                        }
                    } else {

                    }
                } catch(Exception e) {
                    LOG.error("Error with player- " + playerName, e);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
