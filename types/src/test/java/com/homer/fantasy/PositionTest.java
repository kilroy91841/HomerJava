package com.homer.fantasy;

import com.homer.SportType;
import com.homer.dao.MySQLDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 1/29/15.
 */
public class PositionTest {

    @Test
    public void testDo() {
        MySQLDAO dao = MySQLDAO.FACTORY.getInstance();
        List<Position> positions = new ArrayList<Position>();
        Connection connection = dao.getConnection();
        try {

            String sql = "select * from POSITION position";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                int positionId = rs.getInt("positionId");
                String positionName = rs.getString("positionName");
                String positionType = rs.getString("positionType");
                String positionCode = rs.getString("positionCode");

                Position p = Position.get(positionId);
                System.out.println(p);
                Assert.assertEquals((int)p.getPositionId(), positionId);
                Assert.assertEquals(p.getPositionName(), positionName);
                Assert.assertEquals(p.getPositionType(), SportType.getSportType(positionType));
                Assert.assertEquals(p.getPositionCode(), positionCode);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

}
