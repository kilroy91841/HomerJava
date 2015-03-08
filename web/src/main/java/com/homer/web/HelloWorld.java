package com.homer.web;

import com.homer.SportType;
import com.homer.fantasy.dao.ITeamDAO;
import com.homer.fantasy.Team;
import com.homer.web.route.UserAuth;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by arigolub on 2/28/15.
 */
public class HelloWorld implements SparkApplication{

    private static List<Team> teams;

    public static void main(String[] args) throws IOException {
        staticFileLocation("/public");

        ITeamDAO teamDao = ITeamDAO.FACTORY.getInstance();
        teams = teamDao.getTeams(SportType.FANTASY);

        com.homer.web.route.Team.init();
        UserAuth.init();
    }

    public static List<Team> getTeams() { return teams; }

    @Override
    public void init() {
        staticFileLocation("/public");

        ITeamDAO teamDao = ITeamDAO.FACTORY.getInstance();
        teams = teamDao.getTeams(SportType.FANTASY);

        try {
            com.homer.web.route.Team.init();
            UserAuth.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
