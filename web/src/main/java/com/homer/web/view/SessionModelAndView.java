package com.homer.web.view;

import com.homer.web.HelloWorld;
import com.homer.web.authentication.HomerAccount;
import com.homer.web.helpers.PlayerHelper;
import spark.ModelAndView;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arigolub on 3/1/15.
 */
public class SessionModelAndView {

    private static final String SESSION_ACCOUNT = "account";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_TEAMS = "teams";

    public static ModelAndView create(Session session, Object attributes, String viewName) {
        Map<String, Object> attributeMap = (Map<String, Object>) attributes;
        HomerAccount account = session.attribute(SESSION_ACCOUNT);
        attributeMap.put(ATTRIBUTE_USER, account);
        attributeMap.put(ATTRIBUTE_TEAMS, HelloWorld.getTeams());
        attributeMap.put("playerHelper", new PlayerHelper());
        return new ModelAndView(attributeMap, viewName);
    }
}
