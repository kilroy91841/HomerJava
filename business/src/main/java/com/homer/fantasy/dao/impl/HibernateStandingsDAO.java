package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.standings.Standings;
import com.homer.fantasy.standings.StandingsCategory;
import com.homer.fantasy.standings.TeamStandings;
import com.homer.fantasy.standings.TeamStandingsCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by arigolub on 3/2/15.
 */
public class HibernateStandingsDAO extends HomerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateStandingsDAO.class);

    public HibernateStandingsDAO() {
        super();
    }

    public List<TeamStandingsCategory> getTeamStandingsCategories(LocalDate date) {
        LOG.debug("BEGIN: getTeamStandingsCategories [date=" + date + "]");

        TeamStandingsCategory example = new TeamStandingsCategory(null, date, null);
        example.setDate(date);
        List<TeamStandingsCategory> teamStandingsList = findListByExample(example, TeamStandingsCategory.class);

        return teamStandingsList;
    }
    public List<TeamStandings> getTeamStandings(LocalDate date) {
        LOG.debug("BEGIN: getTeamStandings [date=" + date + "]");

        TeamStandings example = new TeamStandings();
        example.setDate(date);
        List<TeamStandings> teamStandingsList = findListByExample(example, TeamStandings.class);

        return teamStandingsList;
    }

    public static void main(String[] args) {
        HibernateStandingsDAO dao = new HibernateStandingsDAO();
        List<TeamStandings> teamStandingses = dao.getTeamStandings(LocalDate.now());
        List<List<TeamStandingsCategory>> outerList1  =
                teamStandingses
                        .stream()
                        .flatMap((teamStandings) -> teamStandings.getTeamStandingsCategoryList().stream())
                        .collect(Collectors.groupingBy(TeamStandingsCategory::getStandingsCategory))
                        .values().stream().collect(Collectors.toList());

        List<TeamStandings> teamStandingsList = Standings.calculate(outerList1);
        for(TeamStandings s : teamStandingsList) {
            System.out.println(s.getTotalPoints() + ", " + s.getPlace() + ", " + s.isTied());
        }
    }
}
