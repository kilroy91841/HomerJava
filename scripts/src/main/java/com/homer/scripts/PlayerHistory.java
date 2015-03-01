package com.homer.scripts;

import com.homer.PlayerStatus;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
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
        IPlayerDAO playerDao = new HibernatePlayerDAO();
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
                    try {
                        if (o.get("espn_player_id") != null) mongoPlayer.setEspn_player_id((int) o.get("espn_player_id"));
                    } catch(ClassCastException e) {
                        mongoPlayer.setEspn_player_id((int) (double) o.get("espn_player_id"));
                    }
                    mongoPlayer.setName_display_first_last(playerName);
                    if (o.get("espn_player_name") != null) mongoPlayer.setEspn_player_name((String)o.get("espn_player_name"));
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
                        if(h.get("fantasy_position") != null) history.setFantasy_position((String)h.get("fantasy_position"));
                        mongoPlayer.getMongoPlayerHistoryList().add(history);
                    }

                    Player example = new Player();
                    example.setPlayerName(mongoPlayer.getName_display_first_last());
                    Player player = playerDao.getPlayer(example);
                    if(player == null) {
                        example = new Player();
                        example.setMlbPlayerId((long)mongoPlayer.getPlayer_id());
                        player = playerDao.getPlayer(example);
                        LOG.debug("Backed up to mlbPlayerId, got player=" + player);
                    }
                    if(player != null) {
                        if(player.getMlbPlayerId() == mongoPlayer.getPlayer_id()) {
                            player.setEspnPlayerId((long)mongoPlayer.getEspn_player_id());
                            player.setEspnPlayerName(mongoPlayer.getEspn_player_name());
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
                                fantasyHistory.setRookieStatus(history.isMinor_leaguer());
                                player.getPlayerHistoryList().add(fantasyHistory);
                                if(history.getYear() == 2014) {
                                    Position fantasyPosition = Position.get(history.getFantasy_position());
                                    PlayerStatus status = PlayerStatus.ACTIVE;
                                    if(history.getFantasy_position().equals("Minors")) {
                                        status = PlayerStatus.MINORS;
                                    }

                                    player.getDailyPlayerInfoList().get(0).setFantasyTeam(new Team(history.getFantasy_team()));
                                    player.getDailyPlayerInfoList().get(0).setFantasyPosition(fantasyPosition);
                                    player.getDailyPlayerInfoList().get(0).setFantasyStatus(status);

                                }
                            }
                            playerDao.createOrSave(player);
                        }
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
