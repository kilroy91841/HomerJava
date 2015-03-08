package com.homer.bridge.job;

import com.homer.espn.Player;
import com.homer.espn.client.ESPNClientREST;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.exception.PlayerNotFoundException;
import com.homer.fantasy.facade.PlayerFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class PlayerUpdateFromESPNLeagueRosterPage implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerUpdateFromESPNLeagueRosterPage.class);

    private static final ESPNClientREST client = new ESPNClientREST();
    private static final PlayerFacade playerFacade = new PlayerFacade();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("BEGIN: execute");

        List<Player> players = client.getRosterPage();
        for(Player p : players) {
            try {
                playerFacade.updateESPNAttributes(p);
            } catch (NoDailyPlayerInfoException e) {
                LOG.error(e.getMessage(), e);
            } catch (PlayerNotFoundException e) {
                LOG.error(e.getMessage(), e);
            } catch (DisallowedTransactionException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        LOG.debug("END: execute");
    }
}
