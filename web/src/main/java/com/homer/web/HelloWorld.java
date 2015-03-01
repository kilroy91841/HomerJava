package com.homer.web;

import com.homer.fantasy.Roster;
import com.homer.fantasy.Team;
import com.homer.fantasy.facade.RosterFacade;
import com.homer.web.authentication.HomerAuth;
import com.homer.web.engine.JadeEngine;
import com.homer.web.transformer.JsonTransformer;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import com.stormpath.sdk.application.*;
import com.stormpath.sdk.account.*;
import com.stormpath.sdk.directory.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by arigolub on 2/28/15.
 */
public class HelloWorld {

    private static final RosterRoute rosterRoute = new RosterRoute();

    public static void main(String[] args) throws IOException {
        get("/team", (req, res) -> {
            int teamId = req.session().attribute("user.teamId");
            res.redirect("team/" + teamId);
            return null;
        }, new JsonTransformer());
        get("/team/:teamId", rosterRoute, new JsonTransformer());
        post("/login", "application/json", (req, res) -> {
           Account loggedInAccount = HomerAuth.login(req.body());
            if(loggedInAccount != null) {
                req.session().attribute("user.teamId", loggedInAccount.getCustomData().get("teamId"));
                res.redirect("/team");
            } else {
                res.redirect("/login");
            }
            return null;
        });
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "Hello World!");

            return new ModelAndView(attributes, "index.jade");
        }, new JadeEngine());
        after("/team/*", (request, response) -> {
            response.type("application/json;charset=ISO-8859-1");
        });
    }

    public static class RosterRoute implements Route {

        private static final RosterFacade rosterFacade = new RosterFacade();

        @Override
        public Object handle(Request request, Response response) throws Exception {
            int teamId = request.params(":teamId") == null ?
                    request.session().attribute("user.teamId") :
                    Integer.parseInt(request.params(":teamId"));
            Roster roster = rosterFacade.getRoster(new Team(teamId), LocalDate.now());
            return roster;
        }
    }
}
