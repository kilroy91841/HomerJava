package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.IllegalVultureException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.IVultureDAO;
import com.homer.fantasy.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/21/15.
 */
public class VultureFacade {

    private static final Logger LOG = LoggerFactory.getLogger(VultureFacade.class);
    private static IVultureDAO dao;

    public VultureFacade() {  dao = IVultureDAO.FACTORY.getInstance(); }

    public Vulture createVulture(Player player, Team vulturingTeam, Player playerToDrop) throws NoDailyPlayerInfoException, IllegalVultureException {
        LOG.debug("BEGIN: createVulture [player=" + player + ", vulturingTeam=" + vulturingTeam + "]");
        Vulture vulture = null;

        //Check for nulls
        if(player == null || player.getPlayerId() == null || vulturingTeam == null || vulturingTeam.getTeamId() == null) {
            LOG.error("Got null input, cannot cretae vulture");
            return vulture;
        }

        //Make sure player exists
        PlayerFacade playerFacade = new PlayerFacade();
        player = playerFacade.getPlayer(player.getPlayerId());
        if(player == null) {
            LOG.debug("Could not find player to vulture");
            return null;
        }

        //Make sure player is vulturable
        boolean playerIsVulturable = playerIsVulturable(player);
        if(!playerIsVulturable) {
            throw new IllegalVultureException("Player is not in a vulturable state");
        }

        //If you are vulturing a player from the DL, you do not need to have a playerToDrop. All other cases,
        //verify that player to drop is owned by team and does not have a vulture out on them or is not vulturable
        if(!PlayerStatus.DISABLEDLIST.equals(player.getMostRecentMLBStatus()) || playerToDrop != null) {

            playerToDrop = playerFacade.getPlayer(playerToDrop.getPlayerId());

            if(!playerToDrop.getCurrentFantasyTeam().getTeamId().equals(vulturingTeam.getTeamId())) {
                throw new IllegalVultureException("Player selected to drop is not owned by vulturing team");
            }

            if(playerIsVulturable(playerToDrop)) {
                throw new IllegalVultureException("Player selected to drop is themselves vulturable, thus the player cannot be used in a vulture");
            }

            List<Vulture> vultures = dao.getVulturesByPlayer(playerToDrop);
            for(Vulture v : vultures) {
                if(Vulture.Status.ACTIVE.equals(v.getVultureStatus())) {
                    throw new IllegalVultureException("Player selected to drop is currently being vultured, thus the player cannot be used in a vulture");
                }
            }
        }

        LOG.debug("All good, creating new vulture");
        vulture = new Vulture();
        vulture.setVultureStatus(Vulture.Status.ACTIVE);
        vulture.setOffendingTeam(player.getCurrentFantasyTeam());
        vulture.setVulturingTeam(vulturingTeam);
        vulture.setPlayer(player);
        vulture.setDroppingPlayer(playerToDrop);
        vulture.setCreatedDate(LocalDateTime.now());
        vulture.setDeadline(LocalDateTime.now().plusDays(1));

        LOG.debug("END: createVulture [vulture=" + vulture + "]");
        return vulture;
    }

    public Vulture resolveVulture(Vulture vulture) {
        LOG.debug("BEGIN: resolveVulture [vulture=" + vulture + "]");

        Player vulturedPlayer = vulture.getPlayer();
        try {
            //Player was dropped, vulture is no longer valid
            if(Team.FANTASY_FREE_AGENT_TEAM == vulturedPlayer.getCurrentFantasyTeam().getTeamId()) {
                LOG.debug("Player was dropped, vulture is resolved");
                vulture.setVultureStatus(Vulture.Status.RESOLVED);
            }

            if(playerIsVulturable(vulturedPlayer)) {
                LOG.debug("Player is still vulturable, granting vulture");

                //Move vultured player to new team
                PlayerFacade playerFacade = new PlayerFacade();
                playerFacade.transferPlayer(vulturedPlayer, vulture.getOffendingTeam(), vulture.getVulturingTeam());

                //Move dropped player to free agency
                Player droppingPlayer = vulture.getDroppingPlayer();
                if(droppingPlayer != null) {
                    playerFacade.transferPlayer(droppingPlayer, vulture.getOffendingTeam(), new Team(Team.FANTASY_FREE_AGENT_TEAM));
                }

                vulture.setVultureStatus(Vulture.Status.GRANTED);
            } else {
                LOG.debug("Player is no longer vulturable, marking as resolved");
                vulture.setVultureStatus(Vulture.Status.RESOLVED);
            }
        } catch (NoDailyPlayerInfoException e) {
            LOG.error("Player did not have latest team, marking as error", e);
            vulture.setVultureStatus(Vulture.Status.ERROR);
        } catch (DisallowedTransactionException e) {
            LOG.error("Attempt to transfer players for granted vulture failed, setting vulture status to error", e);
            vulture.setVultureStatus(Vulture.Status.ERROR);
        }

        vulture = dao.saveVulture(vulture);

        LOG.debug("END: resolveVulture [vulture=" + vulture + "]");
        return vulture;
    }

    public boolean playerIsVulturable(Player player) throws NoDailyPlayerInfoException {
        LOG.debug("BEGIN: playerIsVulturable [player=" + player + "]");
        /*
        Ways a player is vulturable:
        1. player is active in mlb and on disabled list in fantasy
        2. player is on disabled list in mlb and active in fantasy
        3. player is in minor leagues in mlb and active in fantasy
        4. player is on bench in fantasy
        5. player is active in mlb, on minors list in fantasy, and does not have 'rookie status'
         */

        PlayerStatus mlbStatus = player.getMostRecentMLBStatus();
        PlayerStatus fantasyStatus = player.getMostRecentFantasyStatus();
        LOG.debug("Statuses: [mlb=" + mlbStatus + ", fantasyStatus=" + fantasyStatus + "]");

        //Easiest check, if statuses are equal can't vulture
        if(mlbStatus.equals(fantasyStatus)) {
            LOG.debug("Statuses are equal, not vulturable");
            return false;
        }

        //1 and 2
        if((PlayerStatus.ACTIVE.equals(mlbStatus) && PlayerStatus.DISABLEDLIST.equals(fantasyStatus))
                || (PlayerStatus.ACTIVE.equals(fantasyStatus) && PlayerStatus.DISABLEDLIST.equals(mlbStatus))) {
            LOG.debug("Player is vulturable because they are on a disabled list");
            return true;
        }

        //3
        if(PlayerStatus.MINORS.equals(mlbStatus) && !PlayerStatus.MINORS.equals(fantasyStatus)) {
            LOG.debug("Player is vulturable because they are in the minors in MLB but not in fantasy");
            return true;
        }

        //4
        if(PlayerStatus.INACTIVE.equals(fantasyStatus)) {
            LOG.debug("Player is vulturable because they are on the bench in fantasy");
            return true;
        }

        //5
        if((PlayerStatus.ACTIVE.equals(mlbStatus) || PlayerStatus.DISABLEDLIST.equals(mlbStatus)) && PlayerStatus.MINORS.equals(fantasyStatus)) {
            PlayerHistory history = player.getPlayerHistoryList().get(0);
            if(!history.hasRookieStatus()) {
                LOG.debug("Player is vulturable because they are active in MLB, still in Minors in fantasy, and do not have rookie status");
                return true;
            }
        }

        LOG.debug("Player is NOT vulturable");
        return false;
    }
}
