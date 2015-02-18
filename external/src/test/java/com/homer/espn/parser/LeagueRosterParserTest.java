package com.homer.espn.parser;

import com.homer.espn.Player;
import com.homer.mlb.client.MLBClientREST;
import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by arigolub on 2/17/15.
 */
public class LeagueRosterParserTest {

    @Test
    public void parseLeagueRoster() throws IOException {
        String html = getFile("espnLeagueRoster.html", false);
        List<Player> players = LeagueRosterParser.parse(html);
        String playerString = "";
        for(Player p : players) {
            playerString += p + "\n";
        }
        String goldPlayerString = getFile("espnLeagueRoster.txt", true);
        Assert.assertEquals(goldPlayerString, playerString);
    }

    private static String getFile(String fileName, boolean includeNewline) throws IOException
    {
        InputStream stream = LeagueRosterParserTest.class.getClassLoader().
                getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            if(includeNewline) {
                out.append("\n");
            }
        }
        reader.close();
        return out.toString();
    }
}
