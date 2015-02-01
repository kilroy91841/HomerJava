package com.homer.mlb;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBJSONObject extends JSONObject {

    public MLBJSONObject(JSONObject parent) {
        super(parent);
    }

    public Integer getInteger(String key) throws Exception {
        if(!this.has(key)) return null;
        Integer retVal = null;
        try {
            retVal = this.getInt(key);
        } catch(JSONException e) {
            String emptyValue = this.getString(key);
            if(emptyValue == null || !emptyValue.equals("")) {
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    public DateTime getDateTime(String key) throws Exception {
        if(!this.has(key)) return null;
        DateTime retVal = null;
        try {
            retVal = new DateTime(this.get(key));
        } catch(IllegalArgumentException e) {
            String emptyValue = this.getString(key);
            if(emptyValue == null || !emptyValue.equals("")) {
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    @Override
    public String getString(String key) {
        if(!this.has(key)) return null;
        return this.getString(key);
    }

    public Long getLongProtected(String key) {
        if(!this.has(key)) return null;
        return this.getLong(key);
    }

    public Double getDoubleProtected(String key) {
        if(!this.has(key)) return null;
        return this.getDouble(key);
    }
}
