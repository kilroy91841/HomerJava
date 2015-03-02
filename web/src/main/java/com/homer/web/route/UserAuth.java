package com.homer.web.route;

import com.homer.web.authentication.HomerAccount;
import com.homer.web.authentication.IHomerAuth;
import com.homer.web.engine.JadeEngine;
import com.homer.web.view.SessionModelAndView;
import spark.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by arigolub on 3/1/15.
 */
public class UserAuth {

    private static final IHomerAuth homerAuth = IHomerAuth.FACTORY.getInstance();

    public static void init() throws IOException {
        getLoginPage();
        postLoginPage();
    }

    /**
     * Dislays the login page where a user can sign in using their e-mail and password
     * @throws IOException
     */
    private static void getLoginPage() throws IOException {
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("title", "Login");

            return SessionModelAndView.create(request.session(), attributes, "index.jade");
        }, new JadeEngine());
    }

    /**
     * Accepts login info from getLoginPage() and authenticates.
     * On successful login, redirects to team page. On failure, back to getLoginPage()
     */
    private static void postLoginPage() {
        post("/login", "application/json", (req, res) -> {
            HomerAccount loggedInAccount = homerAuth.login(req.body());
            if(loggedInAccount != null) {
                req.session().attribute("account", loggedInAccount);
                res.redirect("/team");
            } else {
                res.redirect("/login");
            }
            return null;
        });
    }
}
