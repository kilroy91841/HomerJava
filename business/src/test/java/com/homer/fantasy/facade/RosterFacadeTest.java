package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by arigolub on 2/22/15.
 */
public class RosterFacadeTest {

    private static IPlayerDAO playerDao = IPlayerDAO.FACTORY.getInstance();
    private static final int TEAM_ID = 1;
    private static RosterFacade rosterFacade = new RosterFacade();
    private static final Team team = new Team(TEAM_ID);
    private static final LocalDate date = LocalDate.now();

    @Before
    public void setup() {
        MockPlayerDAO.clearMap();

        createFantasyTeam();
    }

    @Test
    public void getRoster() {
        Roster roster = rosterFacade.getRoster(team, date);

        Assert.assertEquals(33, roster.getPlayers().size());

        Assert.assertEquals(2, roster.getCatchers().size());
        Assert.assertNotNull(roster.getFirstBase());
        Assert.assertNotNull(roster.getSecondBase());
        Assert.assertNotNull(roster.getThirdBase());
        Assert.assertNotNull(roster.getShortstop());
        Assert.assertNotNull(roster.getMiddleInfield());
        Assert.assertNotNull(roster.getCornerInfield());
        Assert.assertEquals(5, roster.getOutfielders().size());
        Assert.assertNotNull(roster.getUtility());
        Assert.assertEquals(9, roster.getPitchers().size());

        Assert.assertEquals(10, roster.getMinorLeaguers().size());

        roster = rosterFacade.getRoster(team, date.plusDays(1));

        Assert.assertEquals(0, roster.getPlayers().size());
    }

    private void createFantasyTeam() {
        Position[] positions = { Position.FANTASYCATCHER, Position.FANTASYCATCHER, Position.FANTASYFIRSTBASE,
                Position.FANTASYSECONDBASE, Position.FANTASYTHIRDBASE, Position.FANTASYSHORTSTOP, Position.FANTASYCORNERINFIELD,
                Position.FANTASYMIDDLEINFIELD, Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD,
                Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD, Position.FANTASYUTILITY, Position.FANTASYPITCHER,
                Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER,
                Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER };
        int playerCount = 0;
        for(Position p : positions) {
            playerCount++;
            Player player = new Player();
            player.setPlayerId(playerCount);
            player.setPlayerName(p.getPositionName());
            player.addDailyPlayerInfo(getDailyPlayerInfo(player, p, PlayerStatus.ACTIVE));
            playerDao.createOrSave(player);
        }

        for(int i = 24; i < 34; i++) {
            Player minorLeaguer = new Player();
            minorLeaguer.setPlayerId(i);
            minorLeaguer.setPlayerName("MINORLEAGUER " + i);
            minorLeaguer.addDailyPlayerInfo(getDailyPlayerInfo(minorLeaguer, null, PlayerStatus.MINORS));
            playerDao.createOrSave(minorLeaguer);
        }

    }

    private DailyPlayerInfo getDailyPlayerInfo(Player player, Position position, PlayerStatus status) {
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setPlayer(player);
        dpi.setFantasyPosition(position);
        dpi.setFantasyStatus(status);
        dpi.setDate(date);
        dpi.setFantasyTeam(team);
        return dpi;
    }

}
