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

    private static final String URL_LEAGUEROSTERS   = "http://games.espn.go.com/flb/leaguerosters";
    private static final String URL_TRANSACTIONS    = "http://games.espn.go.com/flb/recentactivity";

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

    public static void main(String[] args) {
        ESPNClientREST client = new ESPNClientREST();
        client.getTransactions();
    }

    @Override
    public void getTransactions() {
//        List<Transaction> adds = getTransaction(1, Transaction.ADD);
//        List<Transaction> drops = getTransaction(1, Transaction.DROP);
//        adds.addAll(drops);
//        adds.sort((t1, t2) -> t1.getTime().compareTo(t2.getTime()));
//        for(Transaction t : adds) {
//            System.out.println(t);
//        }
        List<Transaction> trades = getTransaction(12, Transaction.TRADE);

    }

    private List<Transaction> getTransaction(int teamId, Transaction.Type tranType) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_LEAGUEID, VALUE_LEAGUEID);
        parameters.put("seasonId", 2014);
        parameters.put("activityType", 2);
        parameters.put("startDate", 20140401);
        parameters.put("endDate", 20140931);
        parameters.put("teamId", teamId);
        parameters.put("tranType", tranType.getTypeId());
        HttpResponse<InputStream> response = makeRequest(URL_TRANSACTIONS, parameters);
        List<Transaction> transactions = null;
        try {
            String html = IOUtils.toString(response.getBody());
            TransactionsParser parser = new TransactionsParser(teamId, tranType);
            transactions = parser.parse(html);
            for(Transaction t : transactions) {
                System.out.println(t);
            }
        } catch (IOException e) {
            LOG.error("IO exceptoin", e);
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
