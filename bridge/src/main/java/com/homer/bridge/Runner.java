package com.homer.bridge;

import com.homer.bridge.job.DailyPlayerInfoJob;
import com.homer.bridge.job.MLBGameFetch;
import com.homer.bridge.job.PlayerUpdateFromESPNLeagueRosterPage;
import com.homer.bridge.job.PlayerUpdateFromMLB40ManRoster;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
* Created by arigolub on 2/8/15.
*/
public class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    private static Scheduler scheduler;

    public static Scheduler getScheduler() {
        return scheduler;
    }

    private static Trigger mlbUpdatePlayersTrigger = TriggerBuilder.newTrigger()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build();
    private static Trigger espnUpdatePositionsTrigger = TriggerBuilder.newTrigger()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build();
    private static Trigger mlbGameFetchTrigger = TriggerBuilder.newTrigger()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(30).repeatForever()).build();
    private static Trigger dailyPlayerInfoTrigger = TriggerBuilder.newTrigger()
            .withIdentity("dailyPlayerInfoTrigger").build();

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    static {
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            LOG.error("Unable to schedule", e);
        }
    }

    public static void main(String[] args) throws ParseException, SchedulerException {

        JobDetail mlbUpdatePlayersJob = JobBuilder.newJob(PlayerUpdateFromMLB40ManRoster.class)
                .withIdentity("playerUpdateFromMLB40ManRoster")
                .build();
        JobDetail espnUpdatePositionsJob = JobBuilder.newJob(PlayerUpdateFromESPNLeagueRosterPage.class)
                .withIdentity("playerUpdateFromESPNLeagueRosterPage")
                .build();
        JobDetail mlbGameFetchJob = JobBuilder.newJob(MLBGameFetch.class)
                .withIdentity("mlbGameFetch")
                .build();

        scheduler.scheduleJob(espnUpdatePositionsJob, espnUpdatePositionsTrigger);
        scheduler.scheduleJob(mlbUpdatePlayersJob, mlbUpdatePlayersTrigger);
        scheduler.scheduleJob(mlbGameFetchJob, mlbGameFetchTrigger);
    }

    private static JobDetail dailyPlayerInfoJob = JobBuilder.newJob(DailyPlayerInfoJob.class)
            .withIdentity("dailyPlayerInfoJob")
            .build();

    public static JobDetail getDailyPlayerInfoJob() {
        return dailyPlayerInfoJob;
    }

    public static Trigger getDailyPlayerInfoTrigger() {
        return dailyPlayerInfoTrigger;
    }
}
