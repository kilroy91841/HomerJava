package com.homer.web;

import com.homer.fantasy.Player;
import com.homer.fantasy.Roster;
import com.homer.fantasy.Team;
import com.homer.fantasy.facade.RosterFacade;

import java.time.LocalDate;

import static spark.Spark.*;

/**
 * Created by arigolub on 2/28/15.
 */
public class HelloWorld {
    public static void main(String[] args) {
        RosterFacade rosterFacade = new RosterFacade();
        Roster roster = rosterFacade.getRoster(new Team(1), LocalDate.now());
        get("/hello", (req, res) -> roster);
    }
}
