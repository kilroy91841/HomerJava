package com.homer.bridge.job;

import org.quartz.JobExecutionException;

/**
 * Created by arigolub on 5/2/15.
 */
public class DailyPlayerInfoJobApp {

    private static DailyPlayerInfoJob dailyPlayerInfoJob = new DailyPlayerInfoJob();

    public static void main(String[] args) throws JobExecutionException {
        dailyPlayerInfoJob.execute(null);
        System.exit(1);
    }
}
