package com.homer.web.transformer;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.homer.JsonIgnore;
import spark.ResponseTransformer;

/**
 * Created by arigolub on 3/1/15.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            final JsonIgnore expose = fieldAttributes.getAnnotation(JsonIgnore.class);
            return expose != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }).create();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
