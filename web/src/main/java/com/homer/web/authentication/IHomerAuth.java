package com.homer.web.authentication;

import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 3/1/15.
 */
public interface IHomerAuth {

    static final Logger LOG = LoggerFactory.getLogger(IHomerAuth.class);

    public HomerAccount login(String userInfo);

    public HomerAccount signup(String userInfo);

    public static class FACTORY {

        private static IHomerAuth instance = null;

        public static IHomerAuth getInstance() {
            if(instance == null) {
                synchronized (IHomerAuth.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IHomerAuth.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IHomerAuth", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
