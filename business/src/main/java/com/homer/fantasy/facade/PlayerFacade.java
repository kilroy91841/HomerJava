package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.espn.Transaction;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.exception.PlayerNotFoundException;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IPlayerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Created by arigolub on 2/15/15.
 */
public class PlayerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerFacade.class);
    private IPlayerDAO dao;

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

    public Player updateESPNAttributes(com.homer.espn.Player espnPlayer) throws PlayerNotFoundException, NoDailyPlayerInfoException {
        LOG.debug("BEGIN: updateESPNAttributes [espnPlayer=" + espnPlayer + "]");
        Player returnPlayer = null;
        Player player = findESPNPlayer(espnPlayer.getPlayerId(), espnPlayer.getPlayerName());
        if(player != null) {
            if(player.getDailyPlayerInfoList().size() > 0) {
                DailyPlayerInfo dpi = player.getDailyPlayerInfoList().get(0);
                dpi.setFantasyPosition(espnPlayer.getPosition());
                dpi.setFantasyTeam(new Team(espnPlayer.getTeamId()));
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

    public boolean consumeTransaction(Transaction transaction) {
        LOG.debug("BEGIN: consumeTransaction [transaction=" + transaction);
        boolean saved = false;

        LOG.debug("END: consumeTransaction [saved=" + saved + "]");
        return saved;
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
            dbPlayer = createOrUpdatePlayer(existingPlayer);
        }

        LOG.debug("END: createOrUpdatePlayer [mlbPlayer=" + mlbPlayer + "]");
        return dbPlayer;
    }
}
