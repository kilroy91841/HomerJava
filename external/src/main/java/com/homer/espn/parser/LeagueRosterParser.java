package com.homer.espn.parser;

import com.homer.espn.Player;
import com.homer.fantasy.Position;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class LeagueRosterParser {

    private static final Logger LOG = LoggerFactory.getLogger(LeagueRosterParser.class);

    private static final String SELECTOR_PLAYERTD   = "td.playertablePlayerName";
    private static final String SELECTOR_ANCHOR     = "a";

    private static final String ATTR_HREF           = "href";
    private static final String ATTR_PLAYERID       = "playerid";

    private static final String PARAM_TEAMID        = "teamId=";

    public static List<Player> parse(String html) {
        LOG.debug("Parsing [html=" + html + "]");
        Document document = Jsoup.parse(html);
        Elements playerTdList = document.select(SELECTOR_PLAYERTD);

        List<Player> players = null;

        if(playerTdList != null && playerTdList.size() > 0) {
            LOG.debug("Total number of players: " + playerTdList.size());
            players = new ArrayList<Player>();

            for(int i = 0; i < playerTdList.size(); i++) {
                try {
                    String playerName = null;
                    Long playerId = null;
                    int teamId;
                    Position position = null;

                    Element playerTd = playerTdList.get(i);

                    Node teamLink = playerTd.parent().parent().child(0).childNode(0).childNode(0);
                    teamId = new Integer(teamLink.attr(ATTR_HREF).split(PARAM_TEAMID)[1]);

                    Element positionTd = playerTd.parent().child(0);
                    position = Position.get(positionTd.text());

                    Elements playerAnchor = playerTd.select(SELECTOR_ANCHOR);
                    if (playerAnchor != null && playerAnchor.size() > 0) {
                        Element a = playerAnchor.get(0);
                        playerName = a.text();
                        playerId = new Long(a.attr(ATTR_PLAYERID));
                    }

                    Player player = new Player(playerName, playerId, teamId, position);
                    LOG.debug("New ESPN player: " + player);
                    players.add(player);
                } catch(Exception e) {
                    LOG.error("Unexpected runtime error", e);
                }
            }
        }
        return players;
    }
}
