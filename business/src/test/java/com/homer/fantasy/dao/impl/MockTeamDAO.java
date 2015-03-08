package com.homer.fantasy.dao.impl;

import com.homer.SportType;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.ITeamDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 3/7/15.
 */
public class MockTeamDAO implements ITeamDAO {
    @Override
    public List<Team> getTeams(SportType sportType) {
        return new ArrayList<Team>();
    }

    @Override
    public Team getTeam(int teamId) {
        return new Team(teamId);
    }
}
