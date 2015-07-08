package com.homer.bridge.job;

import org.quartz.JobExecutionException;

import java.time.LocalDate;

/**
 * Created by arigolub on 5/2/15.
 */
public class PlayerUpdateFromESPNTransactionsApp {

    private static PlayerUpdateFromESPNTransactions job = new PlayerUpdateFromESPNTransactions().withDate(LocalDate.of(2015, 3, 16));

    public static void main(String[] args) throws JobExecutionException {
        job.execute(null);
    }
}
