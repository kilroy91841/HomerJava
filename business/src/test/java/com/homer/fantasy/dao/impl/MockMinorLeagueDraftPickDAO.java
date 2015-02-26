package com.homer.fantasy.dao.impl;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IMinorLeagueDraftPickDAO;

import java.util.*;

/**
 * Created by arigolub on 2/25/15.
 */
public class MockMinorLeagueDraftPickDAO implements IMinorLeagueDraftPickDAO {

    private static final Map<Integer, MinorLeagueDraftPick> minorLeagueDraftPickMap = new HashMap<Integer, MinorLeagueDraftPick>();

    @Override
    public MinorLeagueDraftPick savePick(MinorLeagueDraftPick minorLeagueDraftPick) {
        addMinorLeagueDraftPick(minorLeagueDraftPick);
        return minorLeagueDraftPick;
    }

    @Override
    public MinorLeagueDraftPick getDraftPick(Team originalTeam, int season, int round) {
        MinorLeagueDraftPick example = new MinorLeagueDraftPick();
        example.setOriginalTeam(originalTeam);
        example.setSeason(season);
        example.setRound(round);
        return minorLeagueDraftPickMap.get(example.hashCode());
    }

    @Override
    public List<MinorLeagueDraftPick> getDraftPicksForTeam(Team owningTeam) {
        List<MinorLeagueDraftPick> picks = new ArrayList<MinorLeagueDraftPick>();
        Set<Integer> keySet = minorLeagueDraftPickMap.keySet();
        for(Integer i : keySet) {
            MinorLeagueDraftPick pick = minorLeagueDraftPickMap.get(i);
            if(pick.getOwningTeam().getTeamId().equals(owningTeam.getTeamId())) {
                picks.add(pick);
            }
        }
        return picks;
    }

    public static void addMinorLeagueDraftPick(MinorLeagueDraftPick pick) {
        minorLeagueDraftPickMap.put(pick.hashCode(), pick);
    }

    public static void clearMap() {
        minorLeagueDraftPickMap.clear();
    }
}
