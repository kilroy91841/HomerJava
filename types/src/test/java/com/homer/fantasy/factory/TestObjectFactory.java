package com.homer.fantasy.factory;

import com.homer.SportType;
import com.homer.fantasy.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arigolub on 1/31/15.
 */
public class TestObjectFactory {

    public static Player getMikeTrout() {
        Player player = new Player(new Long(1), "Mike Trout", Position.CENTERFIELD);
        player.getThirdPartyPlayerInfoList().add(new ThirdPartyPlayerInfo(player, 545361, ThirdPartyPlayerInfo.MLB));
        return player;
    }

    public static Player getMiguelCabrera() {
        return new Player(new Long(2), "Miguel Cabrera", Position.FIRSTBASE);
    }

    public static MinorLeagueDraftPick getFutureDraftPick() {
        Team team = getMarkLorettasScars();
        return new MinorLeagueDraftPick(team, 2015, 1, team, null, null, null, false);
    }

    public static MinorLeagueDraftPick getTradingDraftPick() {
        Team team = getBSnaxx();
        return new MinorLeagueDraftPick(team, 2015, 1, team, null, null, null, null);
    }

    public static Vulture getVulture() {
        Team vulturingTeam = getBSnaxx();
        Team offendingTeam = getMarkLorettasScars();
        Player player = getMikeTrout();
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.HOUR, 16);
        return new Vulture(
                vulturingTeam,
                offendingTeam,
                player,
                cal.getTime(),
                Vulture.ACTIVE
        );
    }

    public static Team getMarkLorettasScars() {
        return new Team(1, "Mark Loretta\'s Scars", SportType.FANTASY, "MLS");
    }

    public static Team getBSnaxx() {
        return new Team(2, "BSnaxx Cracker Jaxx", SportType.FANTASY, "SNAXX");
    }

    public static FreeAgentAuction getFreeAgentAuction() {
        Team requestingTeam = getMarkLorettasScars();
        Player player = getMiguelCabrera();
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        Date createdDate = cal.getTime();
        cal.add(Calendar.DATE, 1);
        Date deadline = cal.getTime();
        return new FreeAgentAuction(requestingTeam, player, createdDate, null, deadline, FreeAgentAuction.ACTIVE);

    }
}
