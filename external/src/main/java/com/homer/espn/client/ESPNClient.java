package com.homer.espn.client;

import com.homer.espn.Player;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public interface ESPNClient {

    public List<Player> getRosterPage();
}
