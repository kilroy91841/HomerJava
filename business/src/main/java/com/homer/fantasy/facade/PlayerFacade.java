package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
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
