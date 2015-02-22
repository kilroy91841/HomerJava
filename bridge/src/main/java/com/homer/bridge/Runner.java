package com.homer.bridge;

import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.job.MLBGameFetch;
import com.homer.job.PlayerUpdateFromESPNTransactions;
import org.quartz.JobExecutionException;

/**
 * Created by arigolub on 2/20/15.
 */
public class Runner {

    public static void main(String[] args) throws DisallowedTransactionException, NoDailyPlayerInfoException {
        MLBGameFetch job = new MLBGameFetch();
        try {
            job.execute(null);
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
