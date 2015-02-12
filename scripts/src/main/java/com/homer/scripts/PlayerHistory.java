package com.homer.scripts;

import com.homer.fantasy.Player;
import com.homer.fantasy.dao.HomerDAO;
import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by arigolub on 2/12/15.
 */
public class PlayerHistory {

    public static void main(String[] args) {
        HomerDAO dao = new HomerDAO();
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("app18596138");
            DBCollection table = db.getCollection("mlbplayers");
            DBCursor cursor = table.find();
            while(cursor.hasNext()) {
                DBObject o = cursor.next();
                int playerId = (int)o.get("player_id");
                String playerName = (String)o.get("name_display_first_last");
                Player player = null;
                try {
                    player = dao.findPlayerByName(playerName);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(player == null) {
                    System.out.println("Couldn't find the player with name " + playerName);
                } else {
                    if(player.getPlayerName().equals(playerName)) {
                        System.out.println("Found " + playerName);
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
