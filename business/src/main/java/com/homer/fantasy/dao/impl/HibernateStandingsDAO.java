package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.standings.Standings;
import com.homer.fantasy.standings.StandingsCategory;
import com.homer.fantasy.standings.TeamStandings;
import com.homer.fantasy.standings.TeamStandingsCategory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

//    public List<TeamStandingsCategory> getTeamStandingsCategories(LocalDate date) {
//        LOG.debug("BEGIN: getTeamStandingsCategories [date=" + date + "]");
//
//        TeamStandingsCategory example = new TeamStandingsCategory(null, date, null);
//        example.setDate(date);
//        List<TeamStandingsCategory> teamStandingsList = findListByExample(example, TeamStandingsCategory.class);
//
//        return teamStandingsList;
//    }

    public List<TeamStandings> getTeamStandings(LocalDate date) {
        LOG.debug("BEGIN: getTeamStandings [date=" + date + "]");

        TeamStandings example = new TeamStandings();
        example.setDate(date);
        List<TeamStandings> teamStandingsList = findListByExample(example, TeamStandings.class);

        return teamStandingsList;
    }
    public TeamStandings getTeamStandings(Team team, LocalDate date) {
        LOG.debug("BEGIN: getTeamStandings [date=" + date + ", team=" + team + "]");

        TeamStandings example = new TeamStandings();
        example.setDate(date);
        example.setTeam(team);
        Session session = null;
        TeamStandings existing = null;
        try {
            session = openSession();
            existing = (TeamStandings) session.createCriteria(TeamStandings.class)
                    .add(Restrictions.like("team", example.getTeam()))
                    .add(Restrictions.like("date", example.getDate()))
                    .uniqueResult();
        } catch(Exception e) {
            LOG.error("Exception finding teams", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return existing;
    }

    public void save(TeamStandings teamStandings) {
        teamStandings.setDate(LocalDate.now());
        TeamStandings existingTeamStandings = getTeamStandings(teamStandings.getTeam(), teamStandings.getDate());
        teamStandings.setTeamStandingsId(existingTeamStandings.getTeamStandingsId());
//        for(TeamStandingsCategory tsc : teamStandings.getTeamStandingsCategoryList()) {
//            saveOrUpdate(tsc);
//        }
        saveOrUpdate(teamStandings);
    }

    public static void main(String[] args) {
        HibernateStandingsDAO dao = new HibernateStandingsDAO();

//        TeamStandings ts = new TeamStandings();
//        ts.setTeam(new Team(2));
//        ts.setDate(LocalDate.now());
//        TeamStandingsCategory tsc = new TeamStandingsCategory();
//        tsc.setStandingsCategory(StandingsCategory.HR);
//        tsc.setTeam(new Team(2));
//        tsc.setDate(LocalDate.now());
//        tsc.setOwner(ts);
//        List<TeamStandingsCategory> tsclist = new ArrayList<TeamStandingsCategory>();
//        tsclist.add(tsc);
//        ts.setTeamStandingsCategoryList(tsclist);
//
//        dao.save(ts);
//        TeamStandings s = dao.getTeamStandings(new Team(1), LocalDate.now());
//        System.out.println(s);
//        s.getTeamStandingsCategoryList().get(0).setCategoryPoints(100.0);
//        dao.save(s);

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
        teamStandingsList.forEach(ts -> dao.save(ts));
//        dao.save(teamStandingsList.get(0));

//        HibernatePlayerDAO playerDAO = new HibernatePlayerDAO();
//        Player player = playerDAO.getPlayer(new Player(1));
//        System.out.println(player);
    }
}
