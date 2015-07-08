package com.homer.web.route;

import com.homer.fantasy.Player;
import com.homer.fantasy.Roster;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.fantasy.facade.RosterFacade;
import com.homer.web.transformer.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

import static spark.Spark.get;

/**
 * Created by arigolub on 5/2/15.
 */
public class Api {

    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    private static final RosterFacade rosterFacade = new RosterFacade();

    public static void init() throws IOException {
        get("/api/team/:teamId", (request, response) -> {
            String teamId = request.params(":teamId");
            com.homer.fantasy.Team team = new com.homer.fantasy.Team(Integer.parseInt(teamId));
            String day = request.queryParams("day");
            String month = request.queryParams("month");
            String year = request.queryParams("year");
            LocalDate localDate = LocalDate.now();
            if(day != null && month != null && year != null) {
                localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            }
            Roster roster = rosterFacade.getRoster(team, localDate);
            response.type("application/json;charset=ISO-8859-1");
            return roster;
        }, new JsonTransformer());
    }
}
