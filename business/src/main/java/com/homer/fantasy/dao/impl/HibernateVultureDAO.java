package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IVultureDAO;
import com.homer.fantasy.Player;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/21/15.
 */
public class HibernateVultureDAO extends HomerDAO implements IVultureDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateVultureDAO.class);

    @Override
    public Vulture saveVulture(Vulture vulture) {
        LOG.debug("BEGIN: saveVulture [vulture=" + vulture + "]");
        boolean success = saveOrUpdate(vulture);
        if(!success) {
            vulture = null;
        }
        LOG.debug("END: saveVulture [vulture=" + vulture + "]");
        return vulture;
    }

    @Override
    public Vulture getVultureById(int id) {
        LOG.debug("BEGIN: getVultureById [id=" + id + "]");
        Vulture vulture = findUniqueById(id, Vulture.class);
        LOG.debug("END: getVultureById [vulture=" + vulture + "]");
        return vulture;
    }

    @Override
    public List<Vulture> getVulturesByPlayer(Player player) {
        LOG.debug("BEGIN: getVulturesByPlayer [player=" + player + "]");
        List<Vulture> vultures = getVultureListWithRestrictions(Restrictions.like("playerId", player.getPlayerId()));
        LOG.debug("END: getVulturesByPlayer [vultures=" + vultures + "]");
        return vultures;
    }

    @Override
    public List<Vulture> getVulturesByStatus(Vulture.Status status) {
        LOG.debug("BEGIN: getVulturesByStatus [status=" + status + "]");
        List<Vulture> vultures = getVultureListWithRestrictions(Restrictions.like("vultureStatus", status));
        LOG.debug("END: getVulturesByStatus [vultures=" + vultures + "]");
        return vultures;
    }

    private List<Vulture> getVultureListWithRestrictions(SimpleExpression restrictions) {
        LOG.debug("BEGIN: getVultureListWithRestrictions [restrictions=" + restrictions + "]");
        List<Vulture> vultures = new ArrayList<Vulture>();
        Session session = null;
        try {
            session = openSession();
            vultures = session.createCriteria(Vulture.class).add(restrictions).list();
        } catch(Exception e) {
            LOG.error("Error finding vultures with restriction " + restrictions, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: getVultureListWithRestrictions");
        return vultures;
    }
}
