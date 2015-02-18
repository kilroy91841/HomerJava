package com.homer.espn.parser;

import com.homer.espn.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/17/15.
 */
public class TransactionsParser {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsParser.class);

    private int teamId;
    private Transaction.Type tranType;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy MMM d h:mm a");

    public TransactionsParser(int teamId, Transaction.Type tranType) {
        this.teamId = teamId;
        this.tranType = tranType;
    }

    public List<Transaction> parse(String html) {
        LOG.debug("Parsing [html=" + html + "]");
        List<Transaction> transactions = new ArrayList<Transaction>();
        Document document = Jsoup.parse(html);
        Elements transactionRows = document.select(".tableBody tr");
        transactionRows.remove(0);
        transactionRows.remove(0);
        for(Element e : transactionRows) {
            Node timeNode = e.childNode(0);
            Node playerNode = e.childNode(2);
            transactions.add(parseTransaction(timeNode, playerNode));
        }
        LOG.debug("Done parsing, list: " + transactions);
        return transactions;
    }

    private Transaction parseTransaction(Node timeNode, Node playerNode) {
        LOG.debug("Parse transaction from nodes [timeNode=" + timeNode + ", playerNode=" + playerNode + "]");
        String playerNodeText = ((Element)playerNode).text();
        System.out.println(playerNodeText);
        String time = ((Element)timeNode).text();
        LocalDateTime dateTime = LocalDateTime.parse("2015" + time.split(",")[1], dateFormatter);
        String playerName = ((Element)playerNode.childNode(1)).text();
        Transaction transaction = new Transaction(playerName, teamId, tranType, dateTime, playerNodeText);
        LOG.debug("Transaction: " + transaction);
        return transaction;
    }
}
