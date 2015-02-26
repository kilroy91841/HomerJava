package com.homer.fantasy.dao;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernateMinorLeagueDraftPickDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/24/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateMinorLeagueDraftPickDAOTest {

    private static final IMinorLeagueDraftPickDAO dao = new HibernateMinorLeagueDraftPickDAO();

    private static Team team;
    private static Team team2;

    @BeforeClass
    public static void setup() {
        team = new Team();
        team2 = new Team();
        team.setTeamId(1);
        team2.setTeamId(2);
    }

    @Test
    public void a_save() {
        MinorLeagueDraftPick minorLeagueDraftPick = new MinorLeagueDraftPick();
        minorLeagueDraftPick.setOriginalTeam(team);
        minorLeagueDraftPick.setOwningTeam(team);
        minorLeagueDraftPick.setRound(1);
        minorLeagueDraftPick.setSeason(2015);

        MinorLeagueDraftPick dbPick = dao.savePick(minorLeagueDraftPick);
        Assert.assertEquals(minorLeagueDraftPick, dbPick);
        Assert.assertNotNull(minorLeagueDraftPick.getMinorLeagueDraftPickId());

        minorLeagueDraftPick = new MinorLeagueDraftPick();
        minorLeagueDraftPick.setOriginalTeam(team);
        minorLeagueDraftPick.setRound(2);
        minorLeagueDraftPick.setSeason(2015);
        minorLeagueDraftPick.setOwningTeam(team2);

        dbPick = dao.savePick(minorLeagueDraftPick);
        Assert.assertEquals(minorLeagueDraftPick, dbPick);
        Assert.assertNotNull(minorLeagueDraftPick.getMinorLeagueDraftPickId());
        Assert.assertNotNull(minorLeagueDraftPick.getOwningTeam());

        minorLeagueDraftPick = new MinorLeagueDraftPick();
        minorLeagueDraftPick.setOriginalTeam(team);
        minorLeagueDraftPick.setOwningTeam(team);
        minorLeagueDraftPick.setRound(3);
        minorLeagueDraftPick.setSeason(2015);
        minorLeagueDraftPick.setOverall(100);
        minorLeagueDraftPick.setDeadline(LocalDateTime.now());
        minorLeagueDraftPick.setSkipped(true);

        dbPick = dao.savePick(minorLeagueDraftPick);
        Assert.assertEquals(minorLeagueDraftPick, dbPick);
        Assert.assertNotNull(minorLeagueDraftPick.getMinorLeagueDraftPickId());
        Assert.assertNotNull(minorLeagueDraftPick.getOverall());
        Assert.assertNotNull(minorLeagueDraftPick.getSkipped());
        Assert.assertNotNull(minorLeagueDraftPick.getDeadline());
    }

    @Test
    public void b_get() {
        MinorLeagueDraftPick minorLeagueDraftPick = dao.getDraftPick(team, 2015, 1);
        Assert.assertNotNull(minorLeagueDraftPick);
        Assert.assertEquals(team, minorLeagueDraftPick.getOriginalTeam());
        Assert.assertEquals(2015, (int)minorLeagueDraftPick.getSeason());
        Assert.assertEquals(1, (int)minorLeagueDraftPick.getRound());
    }

    @Test
    public void c_getList() {
        List<MinorLeagueDraftPick> minorLeagueDraftPickList = dao.getDraftPicksForTeam(team);
        Assert.assertEquals(2, minorLeagueDraftPickList.size());

        minorLeagueDraftPickList = dao.getDraftPicksForTeam(team2);
        Assert.assertEquals(1, minorLeagueDraftPickList.size());
    }

}
