package com.homer.bridge;

import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.Player;
import com.homer.fantasy.Roster;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.job.MLBGameFetch;
import com.homer.job.PlayerUpdateFromESPNLeagueRosterPage;
import com.homer.job.PlayerUpdateFromESPNTransactions;
import com.homer.job.PlayerUpdateFromMLB40ManRoster;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/20/15.
 */
public class Runner {

    public static void main(String[] args) throws Exception {
        //PlayerUpdateFromESPNLeagueRosterPage job = new PlayerUpdateFromESPNLeagueRosterPage();
        //job.execute(null);
//        HibernatePlayerDAO dao = new HibernatePlayerDAO();
//        List<Player> players = dao.getPlayersOnTeamForDate(new Team(1), LocalDate.of(2015, 2, 22));
//        Roster roster = new Roster(players);
<<<<<<< HEAD
=======
        PlayerUpdateFromMLB40ManRoster job = new PlayerUpdateFromMLB40ManRoster();
        job.execute(null);
>>>>>>> 8d8a699555c081622e51c13f356fcc6fe6826dc2
    }
}
