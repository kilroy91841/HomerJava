package com.homer.bridge;

import com.homer.espn.Player;
import com.homer.espn.client.ESPNClient;
import com.homer.espn.client.ESPNClientREST;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.exception.PlayerNotFoundException;
import com.homer.fantasy.facade.PlayerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        PlayerFacade facade = new PlayerFacade();
        ESPNClient client = new ESPNClientREST();
        List<Player> players = client.getRosterPage();
        for(Player p : players) {
            try {
                facade.updateESPNAttributes(p);
            } catch (PlayerNotFoundException e) {
                LOG.error(e.getMessage(), e);
            } catch (NoDailyPlayerInfoException e) {
                LOG.error(e.getMessage(), e);
            }
        }

    }
}
