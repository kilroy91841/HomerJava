package com.homer.web.transformer;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by arigolub on 3/1/15.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
