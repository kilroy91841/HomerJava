package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.*;
import com.homer.fantasy.types.factory.TestObjectFactory;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/31/15.
 */
public class TradeTest {

    @Test
    public void testDo() throws Exception {
        Team proposingTeam = new Team(1, "Mark Loretta\'s Scars", SportType.FANTASY, "MLS");
        Team proposedToTeam = new Team(2, "BSnaxx Cracker Jaxx", SportType.FANTASY, "SNAXX");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR, 13);
        cal.set(Calendar.MINUTE, 30);
        Date createdDate = cal.getTime();
        cal.add(Calendar.DATE, 6);
        Date deadline = cal.getTime();
        List<Tradable> proposingAssets = new ArrayList<Tradable>();
        proposingAssets.add(TestObjectFactory.getMikeTrout());
        proposingAssets.add(TestObjectFactory.getMiguelCabrera());
        List<Tradable> proposedToAssets = new ArrayList<Tradable>();
        proposedToAssets.add(TestObjectFactory.getTradingDraftPick());
        Trade trade = new Trade(proposingTeam, proposedToTeam, createdDate, deadline, proposingAssets, proposedToAssets, Trade.PROPOSED);

        DAO dao = new DAO();
        Trade dbTrade = dao.get();
        Assert.assertEquals(trade, dbTrade);
    }

    private class DAO extends MySQLDAO {
        public Trade get() throws Exception {
            Trade trade = null;
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

                if(rs.first()) {

                    trade = new Trade();
                    trade.setProposingTeam(TypesFactory.createTeam(rs, "proposingTeam"));
                    trade.setProposedToTeam(TypesFactory.createTeam(rs, "proposedToTeam"));
                    trade.setCreatedDate(rs.getTimestamp("trade.createdDate"));
                    trade.setDeadline(rs.getTimestamp("trade.deadline"));
                    trade.setTradeStatus(Trade.TradeStatus.get(rs.getString("trade.tradeStatus")));

                    rs.beforeFirst();
                    while(rs.next()) {
                        String assetType = rs.getString("tradeAsset.assetType");
                        int assetOwningTeamId = rs.getInt("tradeAsset.teamId");
                        Tradable asset = null;
                        if(assetType.equals("PLAYER")) {
                            asset = TypesFactory.createPlayer(rs, "player");
                        } else if(assetType.equals("MONEY")) {
                            asset = new Money(
                                    null,
                                    rs.getInt("money.season"),
                                    Money.MoneyType.get(rs.getString("money.moneyType")),
                                    rs.getInt("money.amount")
                            );
                        } else if(assetType.equals("MINORLEAGUEDRAFTPICK")) {
                            Team originalTeam = TypesFactory.createTeam(rs, "draftPickOriginalTeam");
                            Integer overall = rs.getInt("minorLeagueDraftPick.overall");
                            if(rs.wasNull()) {
                                overall = null;
                            }
                            asset = new MinorLeagueDraftPick(
                                    originalTeam,
                                    rs.getInt("minorLeagueDraftPick.season"),
                                    rs.getInt("minorLeagueDraftPick.round"),
                                    originalTeam,
                                    overall,
                                    null,
                                    null,
                                    null
                            );
                        } else {
                            throw new Exception("Trade asset type " + assetType + " not recognized!");
                        }
                        if(trade.getProposingTeam().getTeamId().equals(assetOwningTeamId)) {
                            trade.getProposingTeamAssets().add(asset);
                        } else if(trade.getProposedToTeam().getTeamId().equals(assetOwningTeamId)) {
                            trade.getProposedToTeamAssets().add(asset);
                        } else {
                            throw new Exception("Trade asset " + asset + " not owned by either team in trade!");
                        }
                    }

                }

                closeAll(rs, statement, connection);

            } catch(SQLException e) {
                e.printStackTrace();;
            }
            return trade;
        }
    }
}
