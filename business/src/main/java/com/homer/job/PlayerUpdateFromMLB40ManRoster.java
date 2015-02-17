package com.homer.job;

import com.homer.SportType;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.mlb.Player;
import com.homer.mlb.client.MLBClientREST;
import com.homer.mlb.parser.JSONRosterParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class PlayerUpdateFromMLB40ManRoster implements Job {

    private static final HomerDAO homerDAO = new HomerDAO();
    private static final Logger LOG = LoggerFactory.getLogger(PlayerUpdateFromMLB40ManRoster.class);
    private static final MLBClientREST client = new MLBClientREST();
    private static final PlayerFacade playerFacade = new PlayerFacade();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("BEGIN: execute");
        List<Team> teams = homerDAO.getTeams(SportType.MLB);
        for(Team t: teams) {
            LOG.debug("Retrieving roster for [team=" + t + "]");
            client.get40ManRosterAsync(t.getTeamId(), callback);
        }
        LOG.debug("END: execute");
    }

    private static Callback<JsonNode> callback = new Callback<JsonNode>() {

        @Override
        public void completed(HttpResponse<JsonNode> httpResponse) {
            try {
                LOG.debug("Request complete, updating players");
                List<Player> players = JSONRosterParser.parseRoster(httpResponse.getBody());
                int playerCount = 0;
                for(Player p : players) {
                    playerCount++;
                    playerFacade.createOrUpdatePlayer(p);
                }
                LOG.debug("Finished updating players, total number of players update: " + playerCount);
            } catch (Exception e) {
                LOG.error("Unexpected error", e);
            }
        }

        @Override
        public void failed(UnirestException e) {
            LOG.error("Failed to retrieve roster", e);
        }

        @Override
        public void cancelled() {
            LOG.debug("Cancelled roster retrieval");
        }
    };
}
