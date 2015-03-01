package com.homer.fantasy.dao.impl;

import com.homer.fantasy.FreeAgentAuction;
import com.homer.fantasy.dao.IFreeAgentAuctionDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/28/15.
 */
public class MockFreeAgentAuctionDAO implements IFreeAgentAuctionDAO {

    private static Map<String, FreeAgentAuction> freeAgentAuctionMap = new HashMap<String, FreeAgentAuction>();
    private static int id = 0;

    @Override
    public FreeAgentAuction saveFreeAgentAuction(FreeAgentAuction freeAgentAuction) {
        freeAgentAuction.setFreeAgentAuctionId(freeAgentAuction.getPlayer().getPlayerId().intValue());
        addToMapByPlayerId(freeAgentAuction);
        return freeAgentAuction;
    }

    @Override
    public FreeAgentAuction getFreeAgentAuction(int playerId) {
        return freeAgentAuctionMap.get(String.valueOf(playerId));
    }

    @Override
    public FreeAgentAuction getFreeAgentAuctionByPlayerId(long playerId) {
        return freeAgentAuctionMap.get(String.valueOf(playerId));
    }

    @Override
    public List<FreeAgentAuction> getFreeAgentAuctionsByStatus(FreeAgentAuction.Status status) {
        List<FreeAgentAuction> list = new ArrayList<FreeAgentAuction>();
        for(FreeAgentAuction faa : freeAgentAuctionMap.values()) {
            if(faa.getStatus().equals(status)) {
                list.add(faa);
            }
        }
        return list;
    }

    public static void addToMapByPlayerId(FreeAgentAuction freeAgentAuction) {
        freeAgentAuctionMap.put(freeAgentAuction.getPlayer().getPlayerId().toString(), freeAgentAuction);
    }

    public static void addToMapById(FreeAgentAuction freeAgentAuction) {
        freeAgentAuctionMap.put(String.valueOf(freeAgentAuction.getFreeAgentAuctionId()), freeAgentAuction);
    }

    public static void clearMap() {
        freeAgentAuctionMap.clear();
    }
}
