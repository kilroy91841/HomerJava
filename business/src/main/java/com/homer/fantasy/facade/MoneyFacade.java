package com.homer.fantasy.facade;

import com.homer.exception.DisallowedTransactionException;
import com.homer.fantasy.Money;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IMoneyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class MoneyFacade {

    private static final int MAJOR_LEAGUE_DRAFT_MINIMUM = 220;

    private static final Logger LOG = LoggerFactory.getLogger(MoneyFacade.class);
    private static IMoneyDAO dao;

    public MoneyFacade() {
        dao = IMoneyDAO.FACTORY.getInstance();
    }

    public List<Money> getMoneyForTeam(Team team) {
        LOG.debug("BEGIN: getMoneyForTeam [team=" + team + "]");

        List<Money> moneyList = dao.getMoneyForTeam(team);

        LOG.debug("END: getMoneyForTeam [moneys=" + moneyList + "]");
        return moneyList;
    }

    public Money transferMoney(Money transferMoney, Team newTeam) throws DisallowedTransactionException {
        LOG.debug("BEGIN: transferMoney [transferMoney=" + transferMoney + ", newTeam=" + newTeam + "]");

        Money oldTeamMoney = dao.getMoney(transferMoney.getTeam().getTeamId(), transferMoney.getSeason(), transferMoney.getMoneyType());
        //Verify old money exists
        if(oldTeamMoney == null) {
            throw new DisallowedTransactionException("Could not find money with key team/season/type: " +
                    transferMoney.getTeam().getTeamId() + "/" + transferMoney.getSeason() + "/" +
                    transferMoney.getMoneyType()
            );
        }

        int possibleNewAmount = oldTeamMoney.getAmount() - transferMoney.getAmount();

        //Make sure amount of money doesn't go below 0
        if(possibleNewAmount < 0) {
            LOG.error("Team id " + oldTeamMoney.getTeam().getTeamId() + " attempt to trade " + oldTeamMoney.getAmount() +
                transferMoney.getAmount() + " dollars puts team below 0, stopping the transfer");
            throw new DisallowedTransactionException("Money transfer would put transferring team below 0");
        }
        //Make sure, if money type is MLB draft, that amount of money doesn't go below allowed threshold
        if(Money.MoneyType.MAJORLEAGUEDRAFT.equals(transferMoney.getMoneyType()) && possibleNewAmount < MAJOR_LEAGUE_DRAFT_MINIMUM) {
            LOG.error("Team id " + oldTeamMoney.getTeam().getTeamId() + " attempt to trade " + oldTeamMoney.getAmount() +
                    transferMoney.getAmount() + " dollars puts team below MLB threshhold of " + MAJOR_LEAGUE_DRAFT_MINIMUM + ", stopping the transfer");
            throw new DisallowedTransactionException("Money transfer would put transferring team below MLB Draft minimum threshold");
        }

        //Checks are good, continue
        LOG.debug("Changing money id " + oldTeamMoney.getMoneyId() + " amount from " + oldTeamMoney.getAmount() + " to " + possibleNewAmount);
        oldTeamMoney.setAmount(possibleNewAmount);

        Money newTeamMoney = dao.getMoney(newTeam.getTeamId(), transferMoney.getSeason(), transferMoney.getMoneyType());
        //Very new money exists
        if(newTeamMoney == null) {
            throw new DisallowedTransactionException("Could not find money with key team/season/type: " +
                    newTeam.getTeamId() + "/" + transferMoney.getSeason() + "/" +
                    transferMoney.getMoneyType()
            );
        }

        int gettingMoneyAmount = newTeamMoney.getAmount() + transferMoney.getAmount();
        LOG.debug("Changing money id " + newTeamMoney.getMoneyId() + " amount from " + newTeamMoney.getAmount() + " to " + gettingMoneyAmount);
        newTeamMoney.setAmount(gettingMoneyAmount);

        LOG.debug("Doing the saves");
        oldTeamMoney = dao.saveMoney(oldTeamMoney);
        if(oldTeamMoney != null) {
            newTeamMoney = dao.saveMoney(newTeamMoney);
        } else {
            LOG.error("Unexpected issue saving money");
            throw new RuntimeException("Unexpected issue saving money ");
        }

        LOG.debug("END: transferMoney [money=" + newTeamMoney + "]");
        return newTeamMoney;
    }
}
