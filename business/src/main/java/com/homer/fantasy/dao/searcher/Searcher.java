package com.homer.fantasy.dao.searcher;

import com.homer.fantasy.Player;
import com.homer.exception.NoDataSearchMethodsProvidedException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/2/15.
 */
public class Searcher<T> {

    private T example;
    private List<DataSearchMethod<T>> methods;

    public Searcher<T> findExample(T example) {
        this.example = example;
        return this;
    }

    public Searcher<T> addSearcher(DataSearchMethod method) {
        if(methods == null) {
            methods = new ArrayList<DataSearchMethod<T>>();
        }
        methods.add(method);
        return this;
    }

    public T search(Connection connection) throws NoDataSearchMethodsProvidedException {
        if(methods == null) {
            throw new NoDataSearchMethodsProvidedException();
        }
        T foundPlayer = null;

        for(DataSearchMethod<T> method : methods) {
            if(method.searchAllowed(example)) {
                foundPlayer = method.find(example, connection);
                if (foundPlayer != null) {
                    break;
                }
            }
        }
        return foundPlayer;
    }
}
