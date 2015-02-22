package com.homer.job;

import com.homer.fantasy.facade.GameFacade;
import com.homer.mlb.Game;
import com.homer.mlb.client.MLBClientREST;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/21/15.
 */
public class MLBGameFetch implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(MLBGameFetch.class);
    private static final MLBClientREST client = new MLBClientREST();
    private static final GameFacade facade = new GameFacade();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("BEGIN: execute");

        LocalDateTime time = LocalDateTime.now().minusHours(5);
        LocalDate date = time.toLocalDate();

        List<Game> games = client.getSchedule(date);
        games.sort((g1, g2) -> g1.getGameTime().compareTo(g2.getGameTime()));
        boolean success = true;
        if(games != null) {
            LOG.debug("Found " + games.size() + " games");
            for (Game g : games) {
                Game dbGame = facade.createOrUpdateGame(g);
                if(dbGame == null) {
                    success = false;
                }
            }

            LOG.debug("Done saving games, success=" + success);

            if(games.size() > 0) {
                LOG.debug("Attempting to schedule DailyPlayerInfoJob");
                Game firstGame = games.get(0);
                LocalDateTime gameTime = firstGame.getGameTime();
                if(LocalDateTime.now().isBefore(gameTime)) {
                    LOG.debug("First game of today is in the future, scheduling for gametime" + gameTime);
                    schedule(gameTime);
                } else {
                    LOG.debug("First game fo today has already happened, job has already ran. Do nothing");
                }
            } else {
                LOG.debug("No games today, so create at 6 am");
                LocalDateTime sixAM = LocalDateTime.now()
                        .withHour(5)
                        .withMinute(0)
                        .withSecond(0);
                schedule(sixAM);
            }

        } else {
            LOG.error("There was an error fetching the games");
        }

        LOG.debug("END: execute");
    }

    private void schedule(LocalDateTime time) {
        try {
            LOG.debug("Scheduling for time=" + time);
            String cronString = getCronString(time);
            LOG.debug("Cron string: " + cronString);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronString))
                    .forJob(Runner.getDailyPlayerInfoJob())
                    .build();
            TriggerKey key = Runner.getDailyPlayerInfoTrigger().getKey();
            if(Runner.getScheduler().checkExists(key)) {
                Runner.getScheduler().rescheduleJob(key, cronTrigger);
            } else {
                Runner.getScheduler().scheduleJob(Runner.getDailyPlayerInfoJob(), cronTrigger);
            }
        } catch (ParseException e) {
            LOG.error("Unable to parse cron schedule", e);
        } catch (SchedulerException e) {
            LOG.error("Unable to schedule job", e);
        }
    }

    private String getCronString(LocalDateTime time) {
        int second = time.getSecond();
        int minute = time.getMinute();
        int hour = time.getHour();
        int dayOfMonth = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year = time.getYear();

        return second + " " +
                minute + " " +
                hour + " " +
                dayOfMonth + " " +
                month + " " +
                "?" + " " +
                year;
    }
}
