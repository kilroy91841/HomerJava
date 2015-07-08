package com.homer.espn.parser;

import com.homer.espn.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arigolub on 2/17/15.
 */
public class TransactionsParser {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsParser.class);

    private static final String SELECTOR_TABLEROWS  = ".tableBody tr";
    private static final String SELECTOR_BR         = "br";

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
        Elements transactionRows = document.select(SELECTOR_TABLEROWS);
        transactionRows.remove(0);
        transactionRows.remove(0);
        for(Element e : transactionRows) {
            if(this.tranType.equals(Transaction.ADD) || this.tranType.equals(Transaction.DROP)) {
                transactions.addAll(new AddDropParser().parseAddDrop(e));
            } else if(this.tranType.equals(Transaction.TRADE)) {
                transactions.addAll(new TradeParser().parseTrade(e));
            } else {
                LOG.warn("Unrecognized tran type " + tranType);
            }
        }
        LOG.debug("Done parsing, list: " + transactions);
        return transactions;
    }

    private class AddDropParser {
        public List<Transaction> parseAddDrop(Element e) {
            LOG.debug("Parsing AddDrops");

            Node timeNode = e.childNode(0);
            Node playerNode = e.childNode(2);

            LOG.debug("Parse transaction from nodes [timeNode=" + timeNode + ", playerNode=" + playerNode + "]");

            String playerNodeText = ((Element)playerNode).text();
            String playerName = ((Element)playerNode.childNode(1)).text();
            playerName = playerName.replace("*", "");

            LocalDateTime dateTime = parseTimeFromNode(timeNode);

            Transaction transaction = new Transaction(playerName, teamId, tranType, dateTime, playerNodeText);
            LOG.debug("Transaction: " + transaction);

            List<Transaction> transactions = new ArrayList<Transaction>();
            transactions.add(transaction);
            return transactions;
        }
    }

    private class TradeParser {
        public List<Transaction> parseTrade(Element e) {
            LOG.debug("Parsing trades");
            if(!e.childNode(1).toString().contains("Trade Upheld")) {
                LOG.debug("Trade is not 'Trade Upheld', delaying parsing until its upheld");
                return new ArrayList<Transaction>();
            }
            Node timeNode = e.childNode(0);
            LocalDateTime dateTime = parseTimeFromNode(timeNode);

            //The trade text only contains team code, not team id, so map the team code in the trade text
            //to the team ids found in the link. Then use these codes to map which team ids received which player
            String teamCode1 = ((Element)e.childNode(3).childNode(0)).text().split(" ")[0];
            int teamId1 = new Integer(e.childNode(3).childNode(0).attr("href").split("teamId=")[1]);
            String teamCode2 = ((Element)e.childNode(3).childNode(2)).text().split(" ")[0];
            int teamId2 = new Integer(e.childNode(3).childNode(2).attr("href").split("teamId=")[1]);

            List<Transaction> transactions = new ArrayList<Transaction>();
            List<List<Node>> individualTradeMoves = new ArrayList<List<Node>>();

            Node tradeTextNode = e.childNode(2);

            List<Node> individualMove = new ArrayList<Node>();
            for(int i = 0; i < tradeTextNode.childNodes().size(); i++) {
                if(tradeTextNode.childNode(i).getClass().equals(TextNode.class)) {
                    individualMove.add(tradeTextNode.childNode(i));
                } else if(tradeTextNode.childNode(i).getClass().equals(Element.class)) {
                    Element e1 = (Element)tradeTextNode.childNode(i);
                    if(e1.tag().getName().equals(SELECTOR_BR)) {
                        individualTradeMoves.add(individualMove);
                        individualMove = new ArrayList<Node>();
                    } else {
                        individualMove.add(e1.childNode(0));
                    }
                }
            }
            if(individualMove.size() > 0) {
                individualTradeMoves.add(individualMove);
            }
            for(List<Node> tradeNodes : individualTradeMoves) {
                Node tradingTeamNode = tradeNodes.get(0);
                Node playerNode = tradeNodes.get(1);
                Node tradedToTeamNode = tradeNodes.get(2);
                String tradingTeamName = tradingTeamNode.toString().split(" ")[0];
                String playerName = playerNode.toString();
                playerName = playerName.replace("*", "");
                String tradedToTeamName = tradedToTeamNode.toString().split("to ")[1];
                Integer acquiringTeamId = null;
                if(tradedToTeamName.equals(teamCode1)) {
                    acquiringTeamId = teamId1;
                } else if(tradedToTeamName.equals(teamCode2)) {
                    acquiringTeamId = teamId2;
                } else {
                    LOG.warn("Could not find acquiring team for this trade");
                }
                Transaction transaction = new Transaction();
                transaction.setTime(dateTime);
                transaction.setPlayerName(playerName);
                transaction.setMove(tranType);
                transaction.setTeamId(acquiringTeamId);
                transaction.setNodeText(tradeNodes.stream().map(x -> x.toString()).collect(Collectors.joining("")));
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    private static LocalDateTime parseTimeFromNode(Node timeNode) {
        String time = ((Element)timeNode).text();
        LocalDateTime dateTime = LocalDateTime.parse("2015" + time.split(",")[1], dateFormatter);
        return dateTime;
    }
}
