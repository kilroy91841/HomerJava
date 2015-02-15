package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.dao.IPlayerDAO;

/**
 * Created by arigolub on 2/15/15.
 */
public class MockPlayerDAO implements IPlayerDAO {

    @Override
    public Player createOrSave(Player player) {
        return player;
    }

    @Override
    public Player getPlayer(Player example) {
        return null;
    }
}
