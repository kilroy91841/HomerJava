package com.homer.fantasy.dao;

import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.exception.StatusNotFoundException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.creator.PlayerCreator;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByMLBPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by arigolub on 2/2/15.
 */
public class HomerDAO extends MySQLDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HomerDAO.class);

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        }catch(SQLException e) {
            LOG.error("SQL Exception closing connection", e);
        }
    }

    public Player findByExample(Player example) {
        Searcher<Player> searcher = new Searcher<Player>().findExample(example)
                .addSearcher(new PlayerSearchByPlayerId())
                .addSearcher(new PlayerSearchByMLBPlayerId())
                .addSearcher(new PlayerSearchByPlayerName());

        Connection connection = getConnection();
        Player player = null;
        try {
            player = searcher.search(connection);
        } catch (NoDataSearchMethodsProvidedException e) {
            LOG.error("No search methods provided", e);
        } finally {
            closeConnection(connection);
        }

        return player;
    }

    public boolean createPlayer(com.homer.mlb.Player mlbPlayer) {
        LOG.info("Creating player {}", mlbPlayer);
        return createPlayer(
                mlbPlayer.getName_display_first_last(),
                mlbPlayer.getPrimary_position(),
                mlbPlayer.getPlayer_id());
    }

    public boolean createPlayer(com.homer.fantasy.Player fantasyPlayer) {
        Long mlbPlayerId = null;
        if(fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null) {
            mlbPlayerId =
                    fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId();
        }
        LOG.info("Creating player {}", fantasyPlayer);
        return createPlayer(
                fantasyPlayer.getPlayerName(),
                fantasyPlayer.getPrimaryPosition().getPositionId(),
                mlbPlayerId);
    }

    private boolean createPlayer(String playerName, int primaryPositionId, Long mlbPlayerId) {
        Connection connection = getConnection();
        PlayerCreator playerCreator = new PlayerCreator(connection);
        boolean success = playerCreator.create(
                playerName,
                primaryPositionId,
                mlbPlayerId);
        closeConnection(connection);
        return success;
    }

    public boolean updatePlayer(Player existingPlayer, Player newPlayer) {
        boolean success = false;
        newPlayer.setPlayerId(existingPlayer.getPlayerId());

        LOG.info("Updating player. Old : {}, New : {}", existingPlayer, newPlayer);

        Connection connection = getConnection();
        try {
            String sql = "update PLAYER " +
                    "set playerName=?, primaryPositionId=?, mlbPlayerId=? " +
                    "where playerId=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPlayer.getPlayerName());
            statement.setInt(2, existingPlayer.getPrimaryPosition().getPositionId());
            Long mlbPlayerId = null;
            if(newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null) {
                mlbPlayerId =
                        newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId();
            }
            if(mlbPlayerId != null) {
                statement.setLong(3, mlbPlayerId);
            } else {
                statement.setNull(3, Types.BIGINT);
            }
            statement.setLong(4, newPlayer.getPlayerId());

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
            success = false;
        }

        closeConnection(connection);

        return success;
    }

    public boolean saveVulture(Vulture vulture) {
        boolean success = false;

        LOG.info("Saving {}", vulture);
        Connection connection = getConnection();
        try {
            String sql = "insert into VULTURE " +
                    "(vulturingTeamId, offendingTeamId, playerId, deadline, vultureStatus) " +
                    "values " +
                    "(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, vulture.getVulturingTeam().getTeamId());
            statement.setInt(2, vulture.getOffendingTeam().getTeamId());
            statement.setLong(3, vulture.getPlayer().getPlayerId());
            statement.setTimestamp(4, new java.sql.Timestamp(vulture.getDeadline().getTime()));
            statement.setString(5, vulture.getStatus().getName());

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }
            statement.close();
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
            success = false;
        }
        closeConnection(connection);
        return success;
    }

    public List<Vulture> getVultures() {
        LOG.info("Finding all vultures");

        List<Vulture> vultures = new ArrayList<Vulture>();
        Connection connection = getConnection();
        try {
            String sql = "select * from VULTURE vulture, TEAM vulturingTeam, TEAM offendingTeam, PLAYER player " +
                    "where vulture.vulturingTeamId = vulturingTeam.teamId " +
                    "and vulture.offendingTeamId = offendingTeam.teamId " +
                    "and player.playerId = vulture.playerId ";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Team vulturingTeam = Team.create(rs, "vulturingTeam");
                Team offendingTeam = Team.create(rs, "offendingTeam");
                Player player = Player.create(rs, "player");
                vultures.add(new Vulture(
                        vulturingTeam,
                        offendingTeam,
                        player,
                        rs.getTimestamp("vulture.deadline"),
                        Vulture.VultureStatus.get(rs.getString("vulture.vultureStatus")))
                );
            }

            closeAll(rs, statement, connection);
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } catch (StatusNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }

        return vultures;
    }

    public boolean createTeam(Team team) {
        boolean success = false;

        Connection connection = getConnection();
        try {
            String sql = "insert into TEAM " +
                    "(teamId, teamName, teamType, teamCode) " +
                    "values " +
                    "(?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, team.getTeamId());
            statement.setString(2, team.getTeamName());
            statement.setString(3, team.getTeamType().getName());
            statement.setString(4, team.getTeamCode());
            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();
            closeConnection(connection);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }

        return success;
    }

    public List<Team> getTeams() {
        LOG.info("Getting teams");
        List<Team> teams = new ArrayList<Team>();
        Connection connection = getConnection();
        try {

            String sql = "select * from TEAM team";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Team t = Team.create(rs, "team");
                teams.add(t);
            }

            closeAll(rs, statement, connection);
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }
        return teams;
    }

    public Team getTeamByName(String teamName) {
        LOG.info("Getting teams");
        Team team = null;
        Connection connection = getConnection();
        try {

            String sql = "select * from TEAM team " +
                    "where teamName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                team = Team.create(rs, "team");
            }

            closeAll(rs, statement, connection);
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }
        return team;
    }

    public boolean createPlayerToTeam(Player fantasyPlayer, java.util.Date gameDate, int fantasyTeamId, int mlbTeamId,
                                              String fantasyPlayerStatusCode, String mlbPlayerStatusCode,
                                              Position fantasyPosition) {
        LOG.info("Creating/updating player to team");
        boolean success = false;
        Connection connection = getConnection();

        try {
            String sql = "insert into PLAYERTOTEAM " +
                    "(playerId, gameDate, fantasyTeamId, mlbTeamId, fantasyPlayerStatusCode, mlbPlayerStatusCode, " +
                    "fantasyPositionId) " +
                    "values " +
                    "(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, fantasyPlayer.getPlayerId());
            statement.setDate(2, new java.sql.Date(gameDate.getTime()));
            statement.setInt(3, fantasyTeamId);
            statement.setInt(4, mlbTeamId);
            statement.setString(5, fantasyPlayerStatusCode);
            statement.setString(6, mlbPlayerStatusCode);
            statement.setInt(7, fantasyPosition.getPositionId());

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();
            closeConnection(connection);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }
        return success;
    }

    public FantasyRoster getFantasyRoster(int teamId, java.util.Date date) {
        Connection connection = getConnection();
        FantasyRoster roster = null;
        try {
            String teamTableName;
            String sql = "select * from PLAYER player, PLAYERTOTEAM playertoteam, TEAM mlbTeam, TEAM fantasyTeam " +
                    "where player.playerId = playertoteam.playerid " +
                    "and fantasyTeam.teamId = playertoteam.fantasyTeamId " +
                    "and mlbTeam.teamId = playertoteam.mlbTeamId " +
                    "and playertoteam.fantasyTeamId = ? " +
                    "and playerToTeam.gameDate = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, teamId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();

            try {
                if(rs.first()) {
                    Team fantasyTeam = Team.create(rs, "fantasyTeam");
                    List<DailyPlayerInfo> players = new ArrayList<DailyPlayerInfo>();
                    rs.beforeFirst();
                    while(rs.next()) {
                        Team mlbTeam = Team.create(rs, "mlbTeam");
                        DailyPlayerInfo p = new DailyPlayerInfo(
                                fantasyTeam,
                                mlbTeam,
                                rs.getDate("playerToTeam.gameDate"),
                                Position.get(rs.getInt("playerToTeam.fantasyPositionId")),
                                null
                        );
                        players.add(p);
                    }
                    roster = new FantasyRoster(fantasyTeam, players);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }

        return roster;
    }

    public List<Trade> createTrade() {
        List<Trade> trades = new ArrayList<Trade>();
        Connection connection = getConnection();
        try {
            String sql = "select * from TRADE trade, TEAM proposingTeam, TEAM proposedToTeam, TRADEASSET tradeAsset " +
                    "left join PLAYER player " +
                    "on player.playerId = tradeAsset.assetId " +
                    "and tradeAsset.assetType =  'PLAYER ' " +
                    "left join MONEY money " +
                    "on money.moneyId = tradeAsset.assetId " +
                    "and tradeAsset.assetType =  'MONEY ' " +
                    "left join MINORLEAGUEDRAFTPICK minorLeagueDraftPick " +
                    "on minorLeagueDraftPick.minorLeagueDraftPickId = tradeAsset.assetId " +
                    "and tradeAsset.assetType =  'MINORLEAGUEDRAFTPICK ' " +
                    "left join TEAM draftPickOriginalTeam " +
                    "on minorLeagueDraftPick.originalTeamId = draftPickOriginalTeam.teamId " +
                    "where  " +
                    "trade.proposingTeamId = proposingTeam.teamId " +
                    "and trade.proposedToTeamid = proposedToTeam.teamId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            trades = Trade.createTradeList(rs);

            closeAll(rs, statement, connection);

        } catch(SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } catch (Exception e) {
            LOG.error("Unexpected runtime error", e);
        }
        return trades;
    }
}
