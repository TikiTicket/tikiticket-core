package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.AuthManager;
import com.tikiticket.core.api.ManagerFactory;
import com.tikiticket.core.api.Profile;
import com.tikiticket.core.api.ProfileManager;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.test.connector.HttpClientConnector;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by veinhorn on 1.4.17.
 */
public class CompositeTest {
    private Connector connector;
    private ManagerFactory managerFactory;

    private AuthManager authManager;
    private ProfileManager profileManager;

    @Before
    public void init() {
        connector = new HttpClientConnector(HttpClients.createDefault());
        managerFactory = new ManagerFactory(connector);
        authManager = managerFactory.newAuthManager();
        profileManager = managerFactory.newProfileManager();
    }

    @Test
    public void compositeTest() throws TikiTicketException {
        boolean isAuthenticated = authManager.authenticate();
        Profile profile = profileManager.getProfile();
        assertEquals("User should be authenticated", isAuthenticated, true);
        assertNotNull("Profile should not be null", profile);
    }
}
