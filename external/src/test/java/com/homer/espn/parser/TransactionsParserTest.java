package com.homer.espn.parser;

import com.homer.espn.Player;
import com.homer.espn.Transaction;
import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by arigolub on 2/18/15.
 */
public class TransactionsParserTest {

    @Test
    public void parseAddDrops() throws IOException {
        String html = LeagueRosterParserTest.getFile("espnTransactionsAdd.html", false);
        TransactionsParser parser = new TransactionsParser(1, Transaction.ADD);
        List<Transaction> adds = parser.parse(html);
        html = LeagueRosterParserTest.getFile("espnTransactionsDrop.html", false);
        parser = new TransactionsParser(1, Transaction.DROP);
        List<Transaction> drops = parser.parse(html);
        adds.addAll(drops);
        adds.sort((t1, t2) -> t1.getTime().compareTo(t2.getTime()));
        String transactionString = "";
        for(Transaction t : adds) {
            transactionString += t + "\n";
        }
        String goldTransactionsString = LeagueRosterParserTest.getFile("espnTransactionsAddDrop.txt", true);
        Assert.assertEquals(goldTransactionsString, transactionString);
    }

    @Test
    public void parseTrades() throws IOException {
        String html = LeagueRosterParserTest.getFile("espnTransactionsTrade.html", false);
        TransactionsParser parser = new TransactionsParser(12, Transaction.TRADE);
        List<Transaction> trades = parser.parse(html);
        String transactionString = "";
        for(Transaction t : trades) {
            transactionString += t + "\n";
        }
        String goldTransactionsString = LeagueRosterParserTest.getFile("espnTransactionsTrade.txt", true);
        Assert.assertEquals(goldTransactionsString, transactionString);
    }
}
