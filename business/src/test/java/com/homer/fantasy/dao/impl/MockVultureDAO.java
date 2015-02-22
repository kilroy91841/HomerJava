package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.IVultureDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/21/15.
 */
public class MockVultureDAO implements IVultureDAO {

    private static final Map<String, Vulture> vultureMap = new HashMap<String, Vulture>();

    public static void addVultureToMap(Vulture vulture) {
        vultureMap.put(vulture.getPlayer().getPlayerName(), vulture);
    }

    public static void clearMap() {
        vultureMap.clear();
    }

    @Override
    public Vulture saveVulture(Vulture vulture) {
        vultureMap.put(vulture.getPlayer().getPlayerName(), vulture);
        return vulture;
    }

    @Override
    public Vulture getVultureById(int id) {
        return null;
    }

    @Override
    public List<Vulture> getVulturesByPlayer(Player player) {
        return new ArrayList<Vulture>();
    }

    @Override
    public List<Vulture> getVulturesByStatus(Vulture.Status status) {
        return null;
    }
}
