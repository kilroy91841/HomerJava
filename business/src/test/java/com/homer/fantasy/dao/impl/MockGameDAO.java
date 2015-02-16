package com.homer.fantasy.dao.impl;

import com.homer.fantasy.dao.IGameDAO;
import com.homer.mlb.Game;

/**
 * Created by arigolub on 2/16/15.
 */
public class MockGameDAO implements IGameDAO {
    @Override
    public Game getGame(Game example) {
        return example;
    }

    @Override
    public Game createOrUpdate(Game game) {
        return game;
    }
}
