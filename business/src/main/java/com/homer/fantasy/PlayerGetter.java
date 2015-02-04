package com.homer.fantasy;

import com.homer.fantasy.dao.BaseballDAO;
import com.homer.mlb.MLBClientREST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.homer.mlb.Player;

import java.util.List;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerGetter {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerGetter.class);

    public static void main(String[] args) {
        LOG.debug("hi");
        BaseballDAO dao = new BaseballDAO();
        List<Team> teams = dao.getTeams();
        MLBClientREST client = new MLBClientREST();
        PlayerFacade facade = new PlayerFacade();
        int playerCount = 0;
        for(Team team : teams) {
            List<Player> mlbPlayerList = client.get40ManRoster(team.getTeamId());
            for(Player player : mlbPlayerList) {
                playerCount++;
                facade.createOrUpdatePlayer(player);
            }
        }
        LOG.info("Players saved: " + playerCount);
    }
}
