package com.homer.fantasy.dao.searcher;

import java.sql.Connection;

/**
 * Created by arigolub on 2/2/15.
 */
public interface DataSearchMethod<T> {

    public boolean searchAllowed(T example);

    public T find(T example, Connection connection);
}
