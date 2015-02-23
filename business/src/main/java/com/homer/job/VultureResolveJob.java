package com.homer.job;

import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.IVultureDAO;
import com.homer.fantasy.facade.VultureFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class VultureResolveJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(VultureResolveJob.class);
    private static final IVultureDAO dao = IVultureDAO.FACTORY.getInstance();
    private static final VultureFacade facade = new VultureFacade();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.debug("BEGIN: execute");

        List<Vulture> activeVultures = dao.getVulturesByStatus(Vulture.Status.ACTIVE);
        LOG.debug("Found " + activeVultures + " active vultures");

        LocalDateTime now = LocalDateTime.now();
        for(Vulture v : activeVultures) {
            if(v.getDeadline().isBefore(now)) {
                LOG.debug("Vulture with id " + v.getVultureId() + " and deadline " + v.getDeadline() + " is being resolved");
                facade.resolveVulture(v);
            }
        }

        LOG.debug("END: execute");
    }
}
