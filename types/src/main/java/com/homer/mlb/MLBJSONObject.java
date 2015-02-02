package com.homer.mlb;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBJSONObject extends JSONObject {

    private JSONObject parent;
    
    public MLBJSONObject(JSONObject parent) {
        this.parent = parent;
    }

    public Integer getInteger(String key) throws Exception {
        if(!parent.has(key)) return null;
        Integer retVal = null;
        try {
            retVal = parent.getInt(key);
        } catch(JSONException e) {
            String emptyValue = parent.getString(key);
            if(emptyValue == null || !emptyValue.equals("")) {
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    public DateTime getDateTime(String key) throws Exception {
        if(!parent.has(key)) return null;
        DateTime retVal = null;
        try {
            retVal = new DateTime(parent.get(key));
        } catch(IllegalArgumentException e) {
            String emptyValue = parent.getString(key);
            if(emptyValue == null || !emptyValue.equals("")) {
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    @Override
    public String getString(String key) {
        if(!parent.has(key)) return null;
        return parent.getString(key);
    }

    public Long getLongProtected(String key) {
        if(!parent.has(key)) return null;
        return parent.getLong(key);
    }

    public Double getDoubleProtected(String key) {
        if(!parent.has(key)) return null;
        return parent.getDouble(key);
    }
}
