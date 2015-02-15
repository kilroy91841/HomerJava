package com.homer.mlb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBJSONObject extends JSONObject {

    private static final Logger LOG = LoggerFactory.getLogger(MLBJSONObject.class);

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
                LOG.debug("Found position " + emptyValue);
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    public LocalDate getLocalDate(String key) throws Exception {
        if(!parent.has(key)) return null;
        LocalDate retVal = null;
        try {
            CharSequence chars = (CharSequence)parent.get(key);
            if(chars == null || chars.length() == 0) {
                return null;
            }
            LocalDateTime dateTime = LocalDateTime.parse(chars);
            retVal = LocalDate.from(dateTime);
        } catch(IllegalArgumentException e) {
            String emptyValue = parent.getString(key);
            if(emptyValue == null || !emptyValue.equals("")) {
                throw new Exception("Something really went wrong finding property [" + key + "]");
            }
        }
        return retVal;
    }

    public LocalDateTime getLocalDateTime(String key) throws Exception {
        if(!parent.has(key)) return null;
        LocalDateTime retVal = null;
        try {
            CharSequence chars = (CharSequence)parent.get(key);
            if(chars == null || chars.length() == 0) {
                return null;
            }
            retVal = LocalDateTime.parse(chars);
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

    @Override
    public JSONObject getJSONObject(String key) {
        return parent.getJSONObject(key);
    }

    @Override
    public JSONArray getJSONArray(String key) {
        return parent.getJSONArray(key);
    }

    @Override
    public boolean has(String key) {
        return parent.has(key);
    }
}
