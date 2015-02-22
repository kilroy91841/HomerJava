package com.homer.espn.client;

import com.homer.espn.Player;
import com.homer.espn.Transaction;
import com.homer.espn.parser.LeagueRosterParser;
import com.homer.espn.parser.TransactionsParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final int SEASON = 2015;

    private static final String URL_LEAGUEROSTERS   = "http://games.espn.go.com/flb/leaguerosters";
    private static final String URL_TRANSACTIONS    = "http://games.espn.go.com/flb/recentactivity";

    private static final String PARAM_LEAGUEID      = "leagueId";
    private static final String VALUE_LEAGUEID      = "216011";


    /**
     * Download and parse roster page into list of players, positions, teams
     * @return list of players
     * Example URL: http://games.espn.go.com/flb/leaguerosters?leagueId=216011
     */
    @Override
    public List<Player> getRosterPage() {
        LOG.debug("BEGIN: getRosterPage");
        List<Player> players = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_LEAGUEID, VALUE_LEAGUEID);
        try {
            LOG.debug("Making request");
            HttpResponse<InputStream> response = makeRequest(URL_LEAGUEROSTERS, parameters);
            LOG.debug("Request finished, parsing");
            String html = IOUtils.toString(response.getBody());
            players = LeagueRosterParser.parse(html);

            LOG.debug("Done parsing");
        } catch (IOException e) {
            LOG.error("IO exception", e);
        }
        int size = players != null ? players.size() : 0;
        LOG.debug("END: getRosterPage, size: " + size);
        return players;
    }

    /**
     * Download and parse transactions from ESPN's Recent Activity page
     * Example URL: http://games.espn.go.com/flb/recentactivity?leagueId=216011&seasonId=2014&activityType=2&startDate=20140701&endDate=20140731&teamId=6&tranType=4
     * @param teamId teamId you are searching for
     * @param tranType transaction type you are searching for
     * @param startDate the date of the first transaction, format: yyyymmdd
     * @param endDate the date of the first transaction, format: yyyymmdd
     * @return list of transactions
     */
    @Override
    public List<Transaction> getTransactions(int teamId, Transaction.Type tranType, String startDate, String endDate) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_LEAGUEID, VALUE_LEAGUEID);
        parameters.put("seasonId", SEASON);
        parameters.put("activityType", 2);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("teamId", teamId);
        parameters.put("tranType", tranType.getTypeId());
        HttpResponse<InputStream> response = makeRequest(URL_TRANSACTIONS, parameters);
        List<Transaction> transactions = null;
        try {
            String html = IOUtils.toString(response.getBody());
            TransactionsParser parser = new TransactionsParser(teamId, tranType);
            transactions = parser.parse(html);
//            for(Transaction t : transactions) {
//                System.out.println(t);
//            }
        } catch (IOException e) {
            LOG.error("IO exception", e);
        }
        return transactions;
    }

    private HttpResponse<InputStream> makeRequest(String url, Map<String, Object> parameters) {
        HttpResponse<InputStream> response = null;
        try {
            response = Unirest.get(url)
                    .queryString(parameters)
                    .asBinary();
        } catch (UnirestException e) {
            LOG.error("Excepting fetching document", e);
        }
        return response;
    }
}
