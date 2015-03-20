package com.homer.web.route;

import com.homer.fantasy.Money;
import com.homer.fantasy.Player;
import com.homer.fantasy.Roster;
import com.homer.fantasy.facade.MoneyFacade;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.fantasy.facade.RosterFacade;
import com.homer.web.HelloWorld;
import com.homer.web.authentication.HomerAccount;
import com.homer.web.engine.JadeEngine;
import com.homer.web.transformer.JsonTransformer;
import com.homer.web.view.SessionModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.after;
import static spark.Spark.get;

/**
 * Created by arigolub on 3/1/15.
 */
public class Team {

    private static final Logger LOG = LoggerFactory.getLogger(Team.class);

    private static final RosterFacade rosterFacade = new RosterFacade();
    private static final MoneyFacade moneyFacade = new MoneyFacade();

    public static void init() throws IOException {
        getTeam();
    }

    /**
     * Allows user to go to team page at /team/:teamId and /team
     */
    private static void getTeam() throws IOException {
        get("/team", (req, res) -> {
            HomerAccount account = req.session().attribute("account");
            res.redirect("team/" + account.getTeamId());
            return null;
        });

//        get("/team/:teamId", rosterRoute, new JsonTransformer());
        get("/team/:teamId", (request, response) -> {
            HomerAccount account = request.session().attribute("account");
            int teamId = request.params(":teamId") == null ?
                    account.getTeamId() :
                    Integer.parseInt(request.params(":teamId"));
            int myTeamId = account.getTeamId();
            Roster roster = rosterFacade.getRoster(new com.homer.fantasy.Team(teamId), LocalDate.now());
            Roster myRoster = rosterFacade.getRoster(new com.homer.fantasy.Team(myTeamId), LocalDate.now());
            Money money = moneyFacade.getMoney(teamId, 2015, Money.MoneyType.MAJORLEAGUEDRAFT);

            LOG.debug("Roster: " + roster);
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("roster", roster);
            attributes.put("myRoster", myRoster);
            attributes.put("money", money);
            return SessionModelAndView.create(request.session(), attributes, "team.jade");
        }, new JadeEngine());
        get("/player/:playerId", (request, response) -> {
            PlayerFacade facade = new PlayerFacade();
            Player player = facade.getPlayer(Long.parseLong(request.params(":playerId")));
            response.type("application/json;charset=ISO-8859-1");
            return player;
        }, new JsonTransformer());
        get("/player", (request, response) -> {
            PlayerFacade facade = new PlayerFacade();
            List<Player> players = facade.getPlayers();
            response.type("application/json;charset=ISO-8859-1");
            return players;
        }, new JsonTransformer());
        get("/player/:playerId", (request, response) -> {
            Roster roster = rosterFacade.getRoster(new com.homer.fantasy.Team(1), LocalDate.now());
            PlayerFacade facade = new PlayerFacade();
            Player player = facade.getPlayer(Long.parseLong(request.params(":playerId")));
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("roster", roster);
            attributes.put("player", player);
            attributes.put("title", player.getPlayerName());
            return SessionModelAndView.create(request.session(), attributes, "player.jade");
        }, new JadeEngine());
        get("/", (request, response) -> {
            HomerAccount account = request.session().attribute("account");
//            if(request.session().isNew() || account == null) {
//                response.redirect("/login");
//                return null;
//            }
            //int teamId =  account.getTeamId();
            int teamId = 1;
            Roster roster = rosterFacade.getRoster(new com.homer.fantasy.Team(teamId), LocalDate.now());
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("myRoster", roster);
           return SessionModelAndView.create(request.session(), attributes, "dashboard.jade");
        }, new JadeEngine());
        get("/sidebar/player/:playerId", (request, response) -> {
            PlayerFacade facade = new PlayerFacade();
            Player player = facade.getPlayer(Long.parseLong(request.params(":playerId")));
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("player", player);
            return SessionModelAndView.create(request.session(), attributes, "mixins/sidebarPlayer.jade");
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
