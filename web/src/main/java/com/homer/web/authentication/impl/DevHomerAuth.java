package com.homer.web.authentication.impl;

import com.homer.web.authentication.HomerAccount;
import com.homer.web.authentication.IHomerAuth;

/**
 * Created by arigolub on 3/1/15.
 */
public class DevHomerAuth implements IHomerAuth {
    @Override
    public HomerAccount login(String userInfo) {
        return new HomerAccount("arigolub@gmail.com", "arigolub@gmail.com", "Ari", 1);
    }

    @Override
    public HomerAccount signup(String userInfo) {
        return null;
    }
}
