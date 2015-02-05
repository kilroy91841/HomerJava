package com.homer.searcher.player;

import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.fantasy.Player;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.searcher.DataSearchMethod;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByMLBPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerName;
import com.homer.fantasy.types.factory.Seeder;
import com.homer.fantasy.types.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by arigolub on 2/3/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerSearchByPlayerIdTest {

    private static HomerDAO dao;
    private long playerId;

    @BeforeClass
    public static void prepare() {
        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("VULTURE", "PLAYER"));

        System.out.println("Preparing db for test VultureTest");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Seeder.seedTable("PLAYER");

        dao = new HomerDAO();
    }

    @Test
    public void test1_PlayerSearchByPlayerName() throws NoDataSearchMethodsProvidedException {
        DataSearchMethod method = new PlayerSearchByPlayerName();
        Player example = new Player();
        example.setPlayerName("Mike Trout");
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example)
                .addSearcher(method);
        Player dbPlayer = searcher.search(dao.getConnection());
        Assert.assertEquals(example.getPlayerName(), dbPlayer.getPlayerName());
        playerId = dbPlayer.getPlayerId();

        DataSearchMethod method2 = new PlayerSearchByPlayerId();
        Player example2 = new Player();
        example2.setPlayerId(playerId);
        Searcher<Player> searcher2 = new Searcher<Player>()
                .findExample(example2)
                .addSearcher(method2);
        Player dbPlayer2 = searcher.search(dao.getConnection());
        Assert.assertEquals(example2.getPlayerId(), dbPlayer2.getPlayerId());
    }

    @Test
    public void testPlayerSearchByMLBPlayerId() throws NoDataSearchMethodsProvidedException {
        DataSearchMethod method = new PlayerSearchByMLBPlayerId();
        Player example = new Player();
        example.getThirdPartyPlayerInfoSet().add(new ThirdPartyPlayerInfo(545361, ThirdPartyPlayerInfo.MLB));
        Searcher<Player> searcher = new Searcher<Player>()
                .findExample(example)
                .addSearcher(method);
        Player dbPlayer = searcher.search(dao.getConnection());
        Assert.assertEquals(example.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId(),
                dbPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
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
