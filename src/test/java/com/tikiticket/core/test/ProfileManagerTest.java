package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.Profile;
import com.tikiticket.core.api.ProfileManager;
import com.tikiticket.core.api.impl.ProfileManagerImpl;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.test.connector.HttpClientConnector;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by veinhorn on 1.4.17.
 */
public class ProfileManagerTest {
    private Connector connector;
    private ProfileManager profileManager;

    @Before
    public void init() {
        connector = new HttpClientConnector(HttpClients.createDefault());
        profileManager = new ProfileManagerImpl(connector);
    }

    @Test
    public void testProfileManager() throws TikiTicketException {
        Profile profile = profileManager.getProfile();
        assertNotNull("Profile should not be null", profile);
    }
}
