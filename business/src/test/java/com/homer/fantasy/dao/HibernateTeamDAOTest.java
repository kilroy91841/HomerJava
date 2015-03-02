package com.homer.fantasy.dao;

import com.homer.SportType;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernateTeamDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by arigolub on 3/1/15.
 */
public class HibernateTeamDAOTest {

    private HibernateTeamDAO dao = new HibernateTeamDAO();

    @Test
    public void getTeams() {
        List<Team> teams = dao.getTeams(SportType.FANTASY);
        Assert.assertEquals(2, teams.size());

        teams = dao.getTeams(SportType.MLB);
        Assert.assertEquals(30, teams.size());
    }

    @Test
    public void getTeam() {
        Team team = dao.getTeam(1);
        Assert.assertNotNull(team);
    }
}
