package com.homer.job;

import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.facade.PlayerFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/21/15.
 */
public class DailyPlayerInfoJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(DailyPlayerInfo.class);
    private static final PlayerFacade facade = new PlayerFacade();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("BEGIN: execute");
        LOG.debug("Running at " + LocalDateTime.now());

        boolean success = facade.createNewDailyPlayerInfoForAll();

        LOG.debug("Done creating new daily player info for all, success=" + success);
        LOG.debug("DONE: execute");
    }
}
