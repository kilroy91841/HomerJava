package com.homer.fantasy.facade;

import com.homer.fantasy.dao.IPlayerDAO;

/**
 * Created by arigolub on 2/22/15.
 */
public class RosterFacade {

    private static IPlayerDAO playerDAO;

    public RosterFacade() {
        playerDAO = IPlayerDAO.FACTORY.getInstance();
    }


}
