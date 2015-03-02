package com.homer.web.authentication;

import com.stormpath.sdk.account.Account;

/**
 * Created by arigolub on 3/1/15.
 */
public class HomerAccount {

    private static final String TEAM_ID = "teamId";

    private String username;
    private String email;
    private String firstname;
    private int teamId;

    public HomerAccount(String username, String email, String firstname, int teamId) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.teamId = teamId;
    }

    public HomerAccount(Account stormpathAccount) {
        this.username = stormpathAccount.getUsername();
        this.email = stormpathAccount.getEmail();
        this.firstname = stormpathAccount.getGivenName();
        this.teamId = (int)stormpathAccount.getCustomData().get(TEAM_ID);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getTeamId() {
        return teamId;
    }
}
