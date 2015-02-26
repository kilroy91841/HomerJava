package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.espn.Transaction;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.exception.PlayerNotFoundException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IPlayerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/15/15.
 */
public class PlayerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerFacade.class);
    private static final int SEASON = 2015;
    private static IPlayerDAO dao;

    public PlayerFacade() {
        dao = IPlayerDAO.FACTORY.getInstance();
    }

    public Player getPlayer(long playerId) {
        LOG.debug("BEGIN: getPlayer [playerId=" + playerId + "]");
        Player player = dao.getPlayer(new Player(playerId));
        LOG.debug("END: getPlayer [player=" + player + "]");
        return player;
    }

    public Player getPlayerFromMLBPlayerId(long mlbPlayerId) {
        LOG.debug("BEGIN: getPlayer [mlbPlayerId=" + mlbPlayerId + "]");
        Player example = new Player();
        example.setMlbPlayerId(mlbPlayerId);
        LOG.debug("Using example player [example=" + example + "]");
        Player player = dao.getPlayer(example);
        LOG.debug("END: getPlayer [player=" + player + "]");
        return player;
    }

    public Player getPlayerByName(String playerName) {
        LOG.debug("BEGIN: getPlayer [playerName=" + playerName + "]");
        Player example = new Player();
        example.setPlayerName(playerName);
        LOG.debug("Using example player [example=" + example + "]");
        Player player = dao.getPlayer(example);
        LOG.debug("END: getPlayer [player=" + player + "]");
        return player;
    }

    public Player updateESPNAttributes(com.homer.espn.Player espnPlayer) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        LOG.debug("BEGIN: updateESPNAttributes [espnPlayer=" + espnPlayer + "]");
        Player returnPlayer = null;
        Player player = findESPNPlayer(espnPlayer.getPlayerId(), espnPlayer.getPlayerName());
        if(player != null) {
            if(player.getDailyPlayerInfoList().size() > 0) {
                DailyPlayerInfo dpi = player.getDailyPlayerInfoList().get(0);
                dpi.setFantasyPosition(espnPlayer.getPosition());
                dpi.setFantasyStatus(Position.getStatusFromPosition(espnPlayer.getPosition()));
                if(player.getEspnPlayerId() == null) {
                    player.setEspnPlayerId(espnPlayer.getPlayerId());
                }
                LOG.debug("New DPI: " + dpi + ", ESPNPLAYERID: " + player.getEspnPlayerId());
                returnPlayer = createOrUpdatePlayer(player);
            } else {
                throw new NoDailyPlayerInfoException(player);
            }
        } else {
            throw new PlayerNotFoundException("Missing player: " + espnPlayer);
        }
        LOG.debug("END: updateESPNAttributes");
        return returnPlayer;
    }

    public Player transferPlayer(Player player, Team oldTeam, Team newTeam) {
        LOG.debug("BEGIN: transferPlayer [player=" + player + ", oldTeam=" + oldTeam + ", newTeam=" + newTeam + "]");
        player.getDailyPlayerInfoList().get(0).setFantasyTeam(newTeam);
        player = dao.createOrSave(player);
        LOG.debug("END: transferPlayer [player=" + player + "]");
        return player;
    }

    private Player findESPNPlayer(Long espnPlayerId, String playerName) {
        LOG.debug("BEGIN: findESPNPlayer [espnPlayerId=" + espnPlayerId + ", playerName=" + playerName);
        Player player = null;
        Player example = new Player();
        if(espnPlayerId != null) {
            LOG.debug("Searching by espnPlayerId");
            example.setEspnPlayerId(espnPlayerId);
            player = dao.getPlayer(example);
            LOG.debug("Found player: " + player);
        }
        if(player == null) {
            LOG.debug("Searching by playerName");
            example = new Player();
            example.setPlayerName(playerName);
            player = dao.getPlayer(example);
            LOG.debug("Found player: " + player);
        }
        if(player == null) {
            LOG.debug("Searching by espnPlayerName");
            example = new Player();
            example.setEspnPlayerName(playerName);
            player = dao.getPlayer(example);
            LOG.debug("Found player: " + player);
        }

        LOG.debug("END: findESPNPlayer [player=" + player + "]");
        return player;
    }

    public Player createOrUpdatePlayer(Player player) {
        LOG.debug("BEGIN: createOrUpdatePlayer [player=" + player + "]");

        Player dbPlayer = dao.createOrSave(player);

        LOG.debug("END: createOrUpdatePlayer [player=" + player + "]");
        return dbPlayer;
    }

    public Player createOrUpdatePlayer(com.homer.mlb.Player mlbPlayer) {
        LOG.debug("BEGIN: createOrUpdatePlayer [mlbPlayer=" + mlbPlayer + "]");

        Player example = new Player();
        example.setMlbPlayerId(mlbPlayer.getPlayer_id());
        LOG.debug("Looking for example player: " + example);
        Player dbPlayer = dao.getPlayer(example);
        if(dbPlayer == null) {
            LOG.debug("Player does not exist, creating new player");
            Player newPlayer = new Player(mlbPlayer);

            PlayerHistory playerHistory = new PlayerHistory();
            playerHistory.setPlayer(newPlayer);
            playerHistory.setSeason(LocalDate.now().getYear());
            playerHistory.setSalary(0);
            newPlayer.addPlayerHistory(playerHistory);

            DailyPlayerInfo dailyPlayerInfo = new DailyPlayerInfo();
            dailyPlayerInfo.setPlayer(newPlayer);
            dailyPlayerInfo.setDate(LocalDate.now());
            dailyPlayerInfo.setMlbTeam(new Team(mlbPlayer.getTeam_id()));
            try {
                dailyPlayerInfo.setMlbStatus(PlayerStatus.get(mlbPlayer.getStatus_code()));
            } catch (Exception e) {
                LOG.error("Unknwon player status: " + mlbPlayer.getStatus() + ", setting status to " + PlayerStatus.UNKNOWN, e);
                dailyPlayerInfo.setMlbStatus(PlayerStatus.UNKNOWN);
            }
            newPlayer.addDailyPlayerInfo(dailyPlayerInfo);

            dbPlayer = createOrUpdatePlayer(newPlayer);
        } else {
            LOG.debug("Player already exists, update player information");
            Player existingPlayer = new Player(mlbPlayer);
            existingPlayer.setPlayerId(dbPlayer.getPlayerId());
            existingPlayer.setDailyPlayerInfoList(dbPlayer.getDailyPlayerInfoList());
            existingPlayer.setPlayerHistoryList(dbPlayer.getPlayerHistoryList());

            try {
                existingPlayer.getDailyPlayerInfoList().get(0).setMlbStatus(PlayerStatus.get(mlbPlayer.getStatus_code()));
            } catch (Exception e) {
                LOG.error("Unknwon player status: " + mlbPlayer.getStatus() + ", setting status to " + PlayerStatus.UNKNOWN, e);
                existingPlayer.getDailyPlayerInfoList().get(0).setMlbStatus(PlayerStatus.UNKNOWN);
            }

            dbPlayer = createOrUpdatePlayer(existingPlayer);
        }

        LOG.debug("END: createOrUpdatePlayer [mlbPlayer=" + mlbPlayer + "]");
        return dbPlayer;
    }

    public boolean createNewDailyPlayerInfoForAll() {
        LOG.debug("BEGIN: createNewDailyPlayerInfoForAll");
        boolean success = true;

        List<Player> players = dao.getPlayersByYear(SEASON);
        for(Player p : players) {
            DailyPlayerInfo latestDailyPlayerInfo = p.getDailyPlayerInfoList().get(0);
            DailyPlayerInfo newDailyPlayerInfo = latestDailyPlayerInfo.copyAndIncrementDay();
            LOG.debug("New DailyPlayerInfo: " + newDailyPlayerInfo);
            p.getDailyPlayerInfoList().add(0, newDailyPlayerInfo);
            p = dao.createOrSave(p);
            if(p == null) {
                success = false;
            }
        }

        LOG.debug("END: createNewDailyPlayerInfoForAll");
        return success;
    }

    public Player demoteToMinorLeagues(Player player) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        LOG.debug("BEGIN: demoteToMinorLeagues [player=" + player + "]");

        Player dbPlayer = getPlayer(player.getPlayerId());
        validatePlayerLists(dbPlayer);

        dbPlayer.getDailyPlayerInfoList().get(0).setFantasyStatus(PlayerStatus.MINORS);

        dbPlayer = createOrUpdatePlayer(dbPlayer);

        LOG.debug("END: demoteToMinorLeagues [player=" + dbPlayer + "]");
        return dbPlayer;
    }

    public Player promoteToMajorLeagues(Player player) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        LOG.debug("BEGIN: promoteToMajorLeagues [player=" + player + "]");

        Player dbPlayer = getPlayer(player.getPlayerId());
        validatePlayerLists(dbPlayer);

        dbPlayer.getDailyPlayerInfoList().get(0).setFantasyStatus(PlayerStatus.ACTIVE);
        dbPlayer.getPlayerHistoryList().get(0).setRookieStatus(false);

        dbPlayer = createOrUpdatePlayer(dbPlayer);

        LOG.debug("END: promoteToMajorLeagues [player=" + dbPlayer + "]");
        return dbPlayer;
    }

    public Player addToSuspendedList(Player player) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        LOG.debug("BEGIN: addToSuspendedList [player=" + player + "]");

        Player dbPlayer = getPlayer(player.getPlayerId());
        validatePlayerLists(dbPlayer);

        dbPlayer.getDailyPlayerInfoList().get(0).setFantasyStatus(PlayerStatus.SUSPENDED);

        dbPlayer = createOrUpdatePlayer(dbPlayer);

        LOG.debug("END: addToSuspendedList [player=" + dbPlayer + "]");
        return dbPlayer;
    }

    private void validatePlayerLists(Player player) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        if(player == null) {
            throw new PlayerNotFoundException("Could not find player with id: " + player.getPlayerId());
        }

        if(player.getDailyPlayerInfoList().get(0) == null) {
            throw new NoDailyPlayerInfoException(player);
        }
    }
}
