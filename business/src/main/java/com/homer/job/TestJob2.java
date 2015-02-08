package com.homer.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/8/15.
 */
public class TestJob2 implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(TestJob2.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("Running job2");
    }
}
