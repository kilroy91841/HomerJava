package com.homer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 1/26/15.
 */
public interface Parsable {

    public void parse(ResultSet rs) throws SQLException;

    public void parse(ResultSet rs, String tableName);
}
