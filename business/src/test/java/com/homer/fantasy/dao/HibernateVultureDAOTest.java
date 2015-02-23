package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
import com.homer.fantasy.dao.impl.HibernateVultureDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/21/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateVultureDAOTest {

    private static final IVultureDAO dao = new HibernateVultureDAO();
    private static int vultureId;

    @Before
    public void setup() {

    }

    @Test
    public void a_saveVulture() {
        Player player = new Player();
        player.setPlayerId(1);
        player.setPlayerName("Vulturing");

        Player droppingPlayer = new Player();
        droppingPlayer.setPlayerId(2);
        droppingPlayer.setPlayerName("Dropping");

        Team vulturingTeam = new Team(1);
        Team offendingTeam = new Team(2);

        Vulture vulture = new Vulture();
        vulture.setPlayer(player);
        vulture.setDroppingPlayer(droppingPlayer);
        vulture.setCreatedDate(LocalDateTime.now());
        vulture.setDeadline(LocalDateTime.now().plusDays(1));
        vulture.setVulturingTeam(vulturingTeam);
        vulture.setOffendingTeam(offendingTeam);
        vulture.setVultureStatus(Vulture.Status.ACTIVE);

        vulture = dao.saveVulture(vulture);
        Assert.assertNotNull(vulture);
        vultureId = vulture.getVultureId();

        vulture = new Vulture();
        vulture.setPlayer(player);
        vulture.setCreatedDate(LocalDateTime.now());
        vulture.setDeadline(LocalDateTime.now().plusDays(1));
        vulture.setVulturingTeam(vulturingTeam);
        vulture.setOffendingTeam(offendingTeam);
        vulture.setVultureStatus(Vulture.Status.RESOLVED);
        vulture = dao.saveVulture(vulture);
        Assert.assertNotNull(vulture);
    }

    @Test
    public void b_getVultureById() {
        Vulture vulture = dao.getVultureById(vultureId);
        Assert.assertNotNull(vulture);
        Assert.assertEquals(Vulture.Status.ACTIVE, vulture.getVultureStatus());
    }

    @Test
    public void c_getVultureByStatus() {
        List<Vulture> actives = dao.getVulturesByStatus(Vulture.Status.ACTIVE);
        List<Vulture> resolveds = dao.getVulturesByStatus(Vulture.Status.RESOLVED);
        Assert.assertNotNull(actives);
        Assert.assertNotNull(resolveds);

        Assert.assertTrue(actives.get(0).getVultureId() != actives.get(1).getVultureId());
    }
}
