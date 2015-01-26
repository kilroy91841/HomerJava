package com.homer.baseball;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MLB on 1/25/15.
 */
public class PlayerTest {

    @Test
    public void doTest() {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/HOMERATTHEBAT", "root", "");

            Statement statement = connection.createStatement();
            String sql = "select * from PLAYER p inner join B_POSITION " + "" +
                    "bp on p.primaryPosition = bp.id";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                long id = rs.getLong("p.id");
                String nameFull = rs.getString("nameFull");
                int positionId = rs.getInt("bp.id");
                Player p = new Player();
                p.setId(id);
                p.setPlayerName(nameFull);
                p.setPrimaryPosition(Position.get(positionId));
                System.out.println(p);
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
