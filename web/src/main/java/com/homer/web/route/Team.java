package com.homer.web.route;

import com.homer.fantasy.Roster;
import com.homer.fantasy.facade.RosterFacade;
import com.homer.web.authentication.HomerAccount;
import com.homer.web.engine.JadeEngine;
import com.homer.web.transformer.JsonTransformer;
import com.homer.web.view.SessionModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.after;
import static spark.Spark.get;

/**
 * Created by arigolub on 3/1/15.
 */
public class Team {

    private static final RosterRoute rosterRoute = new RosterRoute();
    private static final RosterFacade rosterFacade = new RosterFacade();

    public static void init() throws IOException {
        getTeam();

        after("/team/*", (request, response) -> {
            //response.type("application/json;charset=ISO-8859-1");
        });
    }

    /**
     * Allows user to go to team page at /team/:teamId and /team
     */
    private static void getTeam() throws IOException {
        get("/team", (req, res) -> {
            HomerAccount account = req.session().attribute("account");
            res.redirect("team/" + account.getTeamId());
            return null;
        }, new JsonTransformer());

//        get("/team/:teamId", rosterRoute, new JsonTransformer());
        get("/team/:teamId", (request, response) -> {
            int teamId = request.params(":teamId") == null ?
                    request.session().attribute("user.teamId") :
                    Integer.parseInt(request.params(":teamId"));
            Roster roster = rosterFacade.getRoster(new com.homer.fantasy.Team(teamId), LocalDate.now());
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("roster", roster);
            return SessionModelAndView.create(request.session(), attributes, "team.jade");
        }, new JadeEngine());
    }

    public static class RosterRoute implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            int teamId = request.params(":teamId") == null ?
                    request.session().attribute("user.teamId") :
                    Integer.parseInt(request.params(":teamId"));
            Roster roster = rosterFacade.getRoster(new com.homer.fantasy.Team(teamId), LocalDate.now());
            //return roster;
            return SessionModelAndView.create(request.session(), new HashMap<String, Object>(), "team.jade");
        }
    }
}
