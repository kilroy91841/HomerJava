package com.homer.espn.client;

import com.homer.espn.Player;
import com.homer.espn.parser.LeagueRosterParser;
import com.homer.fantasy.Position;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 2/16/15.
 */
public class ESPNClientREST implements ESPNClient {

    private static final Logger LOG = LoggerFactory.getLogger(ESPNClientREST.class);

    private static final String URL_LEAGUEROSTERS   = "http://games.espn.go.com/flb/leaguerosters";

    private static final String PARAM_LEAGUEID      = "leagueId";
    private static final String VALUE_LEAGUEID      = "216011";

    //Example URL: http://games.espn.go.com/flb/leaguerosters?leagueId=216011
    @Override
    public List<Player> getRosterPage() {
        LOG.debug("BEGIN: getRosterPage");
        List<Player> players = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_LEAGUEID, VALUE_LEAGUEID);
        try {
            LOG.debug("Making request");
            HttpResponse<InputStream> response = Unirest.get(URL_LEAGUEROSTERS)
                    .queryString(parameters)
                    .asBinary();
            LOG.debug("Request finished, parsing");
            String html = IOUtils.toString(response.getBody());
            players = LeagueRosterParser.parse(html);
            LOG.debug("Done parsing");
        } catch (UnirestException e) {
            LOG.error("Exception fetching", e);
        } catch (IOException e) {
            LOG.error("IO exception", e);
        }
        int size = players != null ? players.size() : 0;
        LOG.debug("END: getRosterPage, size: " + size);
        return players;
    }

    public static void main(String[] args) {
        ESPNClientREST client = new ESPNClientREST();
        client.getRosterPage();
    }
}
