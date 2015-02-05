package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.FantasyRoster;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.types.factory.TestObjectFactory;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by arigolub on 1/29/15.
 */
public class FantasyRosterTest {

    private static Calendar cal;
    private static final Team fantasyTeam = TestObjectFactory.getMarkLorettasScars();
    private static final Team mlbTeam = new Team(108, "Los Angeles Angels", SportType.MLB, "LAA");

    static {
        cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.DATE, 3);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getDefault());
    }

    @Test
    public void testPojo() {
        List<DailyPlayerInfo> dailies = new ArrayList<DailyPlayerInfo>();

        Map<Integer, Position> positionMap = Position.getPositionMap();
        Set<Integer> keySet = positionMap.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while(iterator.hasNext()) {
            Integer positionKey = iterator.next();
            if(positionMap.get(positionKey).getPositionType().equals(SportType.FANTASY)) {
                if(Position.FANTASYOUTFIELD.getPositionId().equals(positionKey)) {
                    for(int i = 0; i < 5; i++) {
                        dailies.add(generatePlayer(positionMap.get(positionKey)));
                    }
                } else if(Position.FANTASYCATCHER.getPositionId().equals(positionKey)) {
                    for(int i = 0; i < 2; i++) {
                        dailies.add(generatePlayer(positionMap.get(positionKey)));
                    }
                } else if(Position.FANTASYPITCHER.getPositionId().equals(positionKey)) {
                    for(int i = 0; i < 9; i++) {
                        dailies.add(generatePlayer(positionMap.get(positionKey)));
                    }
                } else {
                    dailies.add(generatePlayer(positionMap.get(positionKey)));
                }
            }
        }

        FantasyRoster roster = new FantasyRoster(fantasyTeam, dailies);

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
        Assert.assertNotNull(fantasyTeam);
    }

    @Test
    public void testDB() {
        HomerDAO dao = new HomerDAO();
        FantasyRoster roster = dao.getFantasyRoster(1, cal.getTime());

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
        Assert.assertNotNull(roster.getTeam());

        System.out.println(roster);
    }

    private DailyPlayerInfo generatePlayer(Position position) {
        DailyPlayerInfo player = new DailyPlayerInfo(fantasyTeam, mlbTeam, cal.getTime(), position, null, null, null);
        return player;
    }
}
