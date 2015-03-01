package com.homer.fantasy.dao;

import com.homer.fantasy.FreeAgentAuction;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/28/15.
 */
public interface IFreeAgentAuctionDAO {

    static final Logger LOG = LoggerFactory.getLogger(IFreeAgentAuctionDAO.class);

    public FreeAgentAuction saveFreeAgentAuction(FreeAgentAuction freeAgentAuction);

    public FreeAgentAuction getFreeAgentAuction(int freeAgentAuctionId);

    public FreeAgentAuction getFreeAgentAuctionByPlayerId(long playerId);

    public List<FreeAgentAuction> getFreeAgentAuctionsByStatus(FreeAgentAuction.Status status);

    public static class FACTORY {

        private static IFreeAgentAuctionDAO instance = null;

        public static IFreeAgentAuctionDAO getInstance() {
            if(instance == null) {
                synchronized (IExternalDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IFreeAgentAuctionDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IFreeAgentAuctionDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
