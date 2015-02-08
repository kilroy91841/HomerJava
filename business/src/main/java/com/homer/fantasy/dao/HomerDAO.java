package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.SportType;
import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.exception.StatusNotFoundException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.creator.PlayerCreator;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByESPNPlayerId;
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

    private boolean executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        boolean success = false;
        int rowCount = preparedStatement.executeUpdate();
        if(rowCount > 0) {
            success = true;
        }
        preparedStatement.close();
        return success;
    }

    public Player findByExample(Player example) {
        Searcher<Player> searcher = new Searcher<Player>().findExample(example)
                .addSearcher(new PlayerSearchByPlayerId())
                .addSearcher(new PlayerSearchByMLBPlayerId())
                .addSearcher(new PlayerSearchByESPNPlayerId())
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
                mlbPlayer.getPlayer_id(),
                null);
    }

    public boolean createPlayer(com.homer.fantasy.Player fantasyPlayer) {
        Long mlbPlayerId = null;
        Long espnPlayerId = null;
        if(fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null) {
            mlbPlayerId =
                    fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId();
        }
        if(fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN) != null) {
            espnPlayerId =
                    fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN).getThirdPartyPlayerId();
        }
        LOG.info("Creating player {}", fantasyPlayer);
        return createPlayer(
                fantasyPlayer.getPlayerName(),
                fantasyPlayer.getPrimaryPosition().getPositionId(),
                mlbPlayerId,
                espnPlayerId);
    }

    private boolean createPlayer(String playerName, int primaryPositionId, Long mlbPlayerId, Long espnPlayerId) {
        Connection connection = getConnection();
        PlayerCreator playerCreator = new PlayerCreator(connection);
        boolean success = playerCreator.create(
                playerName,
                primaryPositionId,
                mlbPlayerId,
                espnPlayerId);
        closeConnection(connection);
        return success;
    }

    public boolean updatePlayer(Player existingPlayer, Player newPlayer) throws SQLException {
        boolean success = false;
        newPlayer.setPlayerId(existingPlayer.getPlayerId());

        LOG.info("Updating player. Old : {}, New : {}", existingPlayer, newPlayer);

        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            String sql = "update PLAYER " +
                    "set playerName=?, primaryPositionId=?, mlbPlayerId=?, espnPlayerId=? " +
                    "where playerId=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newPlayer.getPlayerName());
            statement.setInt(2, newPlayer.getPrimaryPosition().getPositionId());
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
            Long espnPlayerId = null;
            if(newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN) != null) {
                espnPlayerId =
                        newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN).getThirdPartyPlayerId();
            }
            if(espnPlayerId != null) {
                statement.setLong(4, espnPlayerId);
            } else {
                statement.setNull(4, Types.BIGINT);
            }
            statement.setLong(5, newPlayer.getPlayerId());

            success = executeUpdate(statement);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
            success = false;
        } finally {
            closeAll(null, statement, connection);
        }

        return success;
    }

//    public boolean saveVulture(Vulture vulture) {
//        boolean success = false;
//
//        LOG.info("Saving {}", vulture);
//        Connection connection = getConnection();
//        try {
//            String sql = "insert into VULTURE " +
//                    "(vulturingTeamId, offendingTeamId, playerId, deadline, vultureStatus) " +
//                    "values " +
//                    "(?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, vulture.getVulturingTeam().getTeamId());
//            statement.setInt(2, vulture.getOffendingTeam().getTeamId());
//            statement.setLong(3, vulture.getPlayer().getPlayerId());
//            statement.setTimestamp(4, new java.sql.Timestamp(vulture.getDeadline().getTime()));
//            statement.setString(5, vulture.getStatus().getName());
//
//            success = executeUpdate(statement);
//
//        } catch (SQLException e) {
//            LOG.error("Something went wrong talking to the database", e);
//            success = false;
//        }
//
//        closeConnection(connection);
//        return success;
//    }
//
//    public List<Vulture> getVultures() {
//        LOG.info("Finding all vultures");
//
//        List<Vulture> vultures = new ArrayList<Vulture>();
//        Connection connection = getConnection();
//        try {
//            String sql = "select * from VULTURE vulture, TEAM vulturingTeam, TEAM offendingTeam, PLAYER player " +
//                    "where vulture.vulturingTeamId = vulturingTeam.teamId " +
//                    "and vulture.offendingTeamId = offendingTeam.teamId " +
//                    "and player.playerId = vulture.playerId ";
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//
//            while(rs.next()) {
//                Team vulturingTeam = Team.create(rs, "vulturingTeam");
//                Team offendingTeam = Team.create(rs, "offendingTeam");
//                Player player = Player.create(rs, "player");
//                vultures.add(new Vulture(
//                        vulturingTeam,
//                        offendingTeam,
//                        player,
//                        rs.getTimestamp("vulture.deadline"),
//                        Vulture.VultureStatus.get(rs.getString("vulture.vultureStatus")))
//                );
//            }
//
//            closeAll(rs, statement, connection);
//        } catch (SQLException e) {
//            LOG.error("Something went wrong talking to the database", e);
//        } catch (StatusNotFoundException e) {
//            LOG.error(e.getMessage(), e);
//        }
//
//        return vultures;
//    }

    public List<Team> getMLBTeams() throws SQLException {
        return getTeams(SportType.MLB);
    }

    public List<Team> getFantasyTeams() throws SQLException {
        return getTeams(SportType.FANTASY);
    }

    public List<Team> getTeams(SportType teamType) throws SQLException {
        LOG.info("Getting teams");
        List<Team> teams = new ArrayList<Team>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {

            String sql = "select * from TEAM team where teamType=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, teamType.getName());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Team t = Team.create(rs, "team");
                teams.add(t);
            }

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, statement, connection);
        }

        return teams;
    }

    public Team getTeamByName(String teamName) throws SQLException {
        LOG.info("Getting teams");
        Team team = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            String sql = "select * from TEAM team " +
                    "where teamName = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, teamName);
            rs = statement.executeQuery();

            while(rs.next()) {
                team = Team.create(rs, "team");
            }

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(rs, statement, connection);
        }

        return team;
    }

    public boolean createPlayerToTeam(Player fantasyPlayer, java.util.Date gameDate, Integer fantasyTeamId, Integer mlbTeamId,
                                              String fantasyPlayerStatusCode, String mlbPlayerStatusCode,
                                              Position fantasyPosition) throws SQLException {
        LOG.info("Creating player to team");
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "insert into PLAYERTOTEAM " +
                    "(playerId, gameDate, fantasyTeamId, mlbTeamId, fantasyPlayerStatusCode, mlbPlayerStatusCode, " +
                    "fantasyPositionId) " +
                    "values " +
                    "(?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, fantasyPlayer.getPlayerId());
            statement.setDate(2, new java.sql.Date(gameDate.getTime()));
            if(fantasyTeamId != null) {
                statement.setInt(3, fantasyTeamId);
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if(mlbTeamId != null) {
                statement.setInt(4, mlbTeamId);
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if(fantasyPlayerStatusCode != null) {
                statement.setString(5, fantasyPlayerStatusCode);
            } else {
                statement.setNull(5, Types.VARCHAR);
            }
            if(mlbPlayerStatusCode != null) {
                statement.setString(6, mlbPlayerStatusCode);
            } else {
                statement.setNull(6, Types.VARCHAR);
            }
            if(fantasyPosition != null) {
                statement.setInt(7, fantasyPosition.getPositionId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

            success = executeUpdate(statement);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, statement, connection);
        }

        return success;
    }

    public boolean updateDailyFantasyProperties(Player fantasyPlayer, Integer fantasyTeamId, PlayerStatus fantasyPlayerStatus,
                                                Position fantasyPosition) throws SQLException {
        LOG.info("Updating fantasy properties");
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "update PLAYERTOTEAM " +
                    "set fantasyTeamId=?, fantasyPlayerStatusCode=?, fantasyPositionId=? " +
                    "where playerId = ? " +
                    "order by gameDate desc " +
                    "limit 1;";
            preparedStatement = connection.prepareStatement(sql);
            if(fantasyTeamId != null) {
                preparedStatement.setInt(1, fantasyTeamId);
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }
            preparedStatement.setString(2, fantasyPlayerStatus.getCode());
            if(fantasyPosition != null) {
                preparedStatement.setInt(3, fantasyPosition.getPositionId());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }

            preparedStatement.setLong(4, fantasyPlayer.getPlayerId());

            success = executeUpdate(preparedStatement);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, preparedStatement, connection);
        }

        return success;
    }

    public boolean updateDailyMLBProperties(Player fantasyPlayer, int mlbTeamId, PlayerStatus mlbPlayerStatus) throws SQLException {
        LOG.info("Updating fantasy properties");
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "update PLAYERTOTEAM " +
                    "set mlbTeamId=?, mlbPlayerStatusCode=? " +
                    "where playerId = ? " +
                    "order by gameDate desc " +
                    "limit 1;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, mlbTeamId);
            preparedStatement.setString(2, mlbPlayerStatus.getCode());
            preparedStatement.setLong(3, fantasyPlayer.getPlayerId());

            success = executeUpdate(preparedStatement);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, preparedStatement, connection);
        }

        return success;
    }

    public boolean createPlayerHistory(Player player, int season, int salary, int keeperSeason, Boolean minorLeaguer,
                                       Team draftTeam, Team keeperTeam, Boolean rookieStatus) throws SQLException {
        LOG.info("Create player history");
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into PLAYERHISTORY (playerId, season, salary, keeperSeason, " + "" +
                    "minorLeaguer, draftTeamId, keeperTeamId, rookieStatus) " +
                    "values " +
                    "(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, player.getPlayerId());
            preparedStatement.setInt(2, season);
            preparedStatement.setInt(3, salary);
            preparedStatement.setInt(4, keeperSeason);
            preparedStatement.setBoolean(5, minorLeaguer);
            if(draftTeam != null) {
                preparedStatement.setInt(6, draftTeam.getTeamId());
            } else {
                preparedStatement.setNull(6, Types.BIGINT);
            }
            if(keeperTeam != null) {
                preparedStatement.setInt(7, keeperTeam.getTeamId());
            } else {
                preparedStatement.setNull(7, Types.BIGINT);
            }
            preparedStatement.setBoolean(8, rookieStatus);

            success = executeUpdate(preparedStatement);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, preparedStatement, connection);
        }

        return success;
    }

    public boolean updatePlayerHistory(Player player, PlayerHistory history) throws SQLException {
        LOG.info("Updating player history");
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "update PLAYERHISTORY " +
                    "set salary=?, keeperSeason=?, minorLeaguer=?, draftTeamId=?, keeperTeamId=?, rookieStatus=? " +
                    "where playerId=? and season=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, history.getSalary());
            preparedStatement.setInt(2, history.getKeeperSeason());
            preparedStatement.setBoolean(3, history.isMinorLeaguer());
            if(history.getDraftTeam() != null) {
                preparedStatement.setInt(4, history.getDraftTeam().getTeamId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            if(history.getKeeperTeam() != null) {
                preparedStatement.setInt(5, history.getKeeperTeam().getTeamId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }
            preparedStatement.setBoolean(6, history.hasRookieStatus());
            preparedStatement.setLong(7, player.getPlayerId());
            preparedStatement.setInt(8, history.getSeason());

            success = executeUpdate(preparedStatement);
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(null, preparedStatement, connection);
        }

        return success;
    }

    public List<PlayerHistory> getPlayerHistory(Player player) throws Exception {
        LOG.info("Getting player history");
        if(player == null || player.getPlayerId() == null) {
            throw new Exception("PlayerId must be set");
        }
        List<PlayerHistory> playerHistoryList = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = getConnection();

        try {
            String sql = "select * from PLAYERHISTORY playerHistory " +
                    "left join TEAM draftTeam on playerHistory.draftTeamId = draftTeam.teamId " +
                    "left join TEAM keeperTeam on playerHistory.keeperTeamId = keeperTeam.teamId " +
                    "where playerHistory.playerId=? " +
                    "order by playerHistory.season desc";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, player.getPlayerId());
            rs = statement.executeQuery();

            playerHistoryList = new ArrayList<PlayerHistory>();
            while(rs.next()) {
                Team draftTeam = Team.create(rs, "draftTeam");
                Team keeperTeam = Team.create(rs, "keeperTeam");
                PlayerHistory history = new PlayerHistory(
                        rs.getInt("playerHistory.season"),
                        rs.getInt("playerHistory.salary"),
                        rs.getInt("playerHistory.keeperSeason"),
                        rs.getBoolean("playerHistory.minorLeaguer"),
                        draftTeam,
                        keeperTeam,
                        rs.getBoolean("playerHistory.rookieStatus")
                );
                playerHistoryList.add(history);
            }
        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        } finally {
            closeAll(rs, statement, connection);
        }

        return playerHistoryList;
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
                                PlayerStatus.get(rs.getString("playerToTeam.fantasyPlayerStatusCode")),
                                PlayerStatus.get(rs.getString("playerToTeam.mlbPlayerStatusCode")),
                                null
                        );
                        players.add(p);
                    }
                    roster = new FantasyRoster(fantasyTeam, players);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            closeAll(rs, statement, connection);

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
        }

        return roster;
    }

//    public List<Trade> createTrade() {
//        List<Trade> trades = new ArrayList<Trade>();
//        Connection connection = getConnection();
//        try {
//            String sql = "select * from TRADE trade, TEAM proposingTeam, TEAM proposedToTeam, TRADEASSET tradeAsset " +
//                    "left join PLAYER player " +
//                    "on player.playerId = tradeAsset.assetId " +
//                    "and tradeAsset.assetType =  'PLAYER ' " +
//                    "left join MONEY money " +
//                    "on money.moneyId = tradeAsset.assetId " +
//                    "and tradeAsset.assetType =  'MONEY ' " +
//                    "left join MINORLEAGUEDRAFTPICK minorLeagueDraftPick " +
//                    "on minorLeagueDraftPick.minorLeagueDraftPickId = tradeAsset.assetId " +
//                    "and tradeAsset.assetType =  'MINORLEAGUEDRAFTPICK ' " +
//                    "left join TEAM draftPickOriginalTeam " +
//                    "on minorLeagueDraftPick.originalTeamId = draftPickOriginalTeam.teamId " +
//                    "where  " +
//                    "trade.proposingTeamId = proposingTeam.teamId " +
//                    "and trade.proposedToTeamid = proposedToTeam.teamId";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//
//            trades = Trade.createTradeList(rs);
//
//            closeAll(rs, statement, connection);
//
//        } catch(SQLException e) {
//            LOG.error("Something went wrong talking to the database", e);
//        } catch (Exception e) {
//            LOG.error("Unexpected runtime error", e);
//        }
//        return trades;
//    }
}
