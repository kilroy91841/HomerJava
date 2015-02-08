package com.homer.job;

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

    public static void main(String[] args) throws ParseException, SchedulerException {
        JobDetail job = JobBuilder.newJob(TestJob.class)
                .withIdentity("job1")
                .build();

        JobDetail job2 = JobBuilder.newJob(TestJob2.class)
                .withIdentity("job2")
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("crontrigger","crontriggergroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * 15 * * ?"))
                .forJob(job)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, cronTrigger);
        scheduler.scheduleJob(job2, trigger);
    }
}
