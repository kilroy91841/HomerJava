package com.homer;

import java.sql.ResultSet;

/**
 * Created by arigolub on 1/26/15.
 */
public interface Parsable {

    public void parse(ResultSet rs);
}
