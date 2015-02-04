package com.homer.searcher.player;

import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.fantasy.Player;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.searcher.DataSearchMethod;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByMLBPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerName;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by arigolub on 2/3/15.
 */
public class PlayerSearchByPlayerIdTest {

    private static final HomerDAO dao = new HomerDAO();

    @Test
    public void testPlayerSearchByPlayerId() throws NoDataSearchMethodsProvidedException {
        DataSearchMethod method = new PlayerSearchByPlayerId();
        Player example = new Player();
        example.setPlayerId(1L);
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example)
                .addSearcher(method);
        Player dbPlayer = searcher.search(dao.getConnection());
        Assert.assertEquals(example.getPlayerId(), dbPlayer.getPlayerId());
    }

    @Test
    public void testPlayerSearchByPlayerName() throws NoDataSearchMethodsProvidedException {
        DataSearchMethod method = new PlayerSearchByPlayerName();
        Player example = new Player();
        example.setPlayerName("Mike Trout");
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example)
                .addSearcher(method);
        Player dbPlayer = searcher.search(dao.getConnection());
        Assert.assertEquals(example.getPlayerName(), dbPlayer.getPlayerName());
    }

    @Test
    public void testPlayerSearchByMLBPlayerId() throws NoDataSearchMethodsProvidedException {
        DataSearchMethod method = new PlayerSearchByMLBPlayerId();
        Player example = new Player();
        example.getThirdPartyPlayerInfoList().add(new ThirdPartyPlayerInfo(example, 545361, ThirdPartyPlayerInfo.MLB));
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example)
                .addSearcher(method);
        Player dbPlayer = searcher.search(dao.getConnection());
        Assert.assertEquals(example.getThirdPartyPlayerInfoList().get(0).getThirdPartyPlayerId(),
                dbPlayer.getThirdPartyPlayerInfoList().get(0).getThirdPartyPlayerId());
    }

    @Test
    public void testNoDataSearchMethodsProvidedException() {
        Player example = new Player();
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example);
        try {
            Player dbPlayer = searcher.search(dao.getConnection());
            Assert.fail();
        } catch (NoDataSearchMethodsProvidedException e) {
            Assert.assertTrue(true);
        }
    }
}
