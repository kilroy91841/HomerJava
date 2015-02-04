package com.homer.fantasy.dao.creator;

import com.homer.fantasy.dao.HomerDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by arigolub on 2/4/15.
 */
public class DailyPlayerCreatorTest {

    private HomerDAO dao = new HomerDAO();

    @Test
    public void createDailyPlayer() {
        long playerId = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Integer fantasyTeamId = 1;
        Integer mlbTeamId = 147;
        Integer fantasyPlayerStatusId = 1;
        Integer mlbPlayerStatusId = 1;
        Integer fantasyPositionId = 107;

        DailyPlayerCreator creator = new DailyPlayerCreator(dao.getConnection());
        boolean success =
                creator.createOrUpdate(playerId, cal.getTime(), fantasyTeamId, mlbTeamId,
                        fantasyPlayerStatusId, mlbPlayerStatusId, fantasyPositionId);
        Assert.assertTrue(success);
    }
}
