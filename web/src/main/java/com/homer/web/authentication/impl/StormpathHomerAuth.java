package com.homer.web.authentication.impl;

import com.homer.web.authentication.HomerAccount;
import com.homer.web.authentication.IHomerAuth;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.sdk.tenant.Tenant;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.net.URLCodec;

import java.net.URL;

/**
 * Created by arigolub on 3/1/15.
 */
public class StormpathHomerAuth implements IHomerAuth {

    //TODO write tests for this class

    private static final Logger LOG = LoggerFactory.getLogger(StormpathHomerAuth.class);

    private static final String API_FILE_RELATIVE_PATH = ".stormpath/apiKey.properties";
    private static final String APPLICATION_NAME = "HomerAtTheBat";

    private static URL url = StormpathHomerAuth.class.getClassLoader().getResource(API_FILE_RELATIVE_PATH);
    private static ApiKey apiKey = ApiKeys.builder().setFileLocation(url.getPath()).build();
    private static Client client = Clients.builder().setApiKey(apiKey).build();

    private static Tenant tenant = client.getCurrentTenant();
    private static ApplicationList applications = tenant.getApplications(
            Applications.where(Applications.name().eqIgnoreCase(APPLICATION_NAME))
    );

    private static Application application = applications.iterator().next();

    private static URLCodec codec = new URLCodec();

    public HomerAccount login(String userInfo) {
        HomerAccount myAccount = null;
        try {
            String[] params = userInfo.split("&");
            String username = codec.decode(params[0]).split("=")[1];
            String password = codec.decode(params[1]).split("=")[1];
            AuthenticationRequest authenticationRequest = new UsernamePasswordRequest(username, password);
            AuthenticationResult authenticationResult = application.authenticateAccount(authenticationRequest);
            Account stormpathAccount = authenticationResult.getAccount();
            myAccount = new HomerAccount(stormpathAccount);
        } catch (ResourceException e) {
            LOG.error(e.getDeveloperMessage(), e);
        } catch (DecoderException e) {
            LOG.error(e.getMessage(), e);
        }
        return myAccount;
    }

    public HomerAccount signup(String userInfo) {
        HomerAccount homerAccount = null;
        Account account = client.instantiate(Account.class);
        account.setGivenName("Ari");
        account.setSurname("Golub");
        //account.setUsername("tk421"); //optional, defaults to email if unset
        account.setEmail("arigolub@gmail.com");
        account.setPassword("Tester123");
        CustomData customData = account.getCustomData();
        customData.put("teamId", 1);
        Account myAccount = null;
        try {
            myAccount = application.createAccount(account);
            homerAccount = new HomerAccount(myAccount);
        } catch (ResourceException e) {
            LOG.error(e.getDeveloperMessage(), e);
        }
        return homerAccount;
    }
}
