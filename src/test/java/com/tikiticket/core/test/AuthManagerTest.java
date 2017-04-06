package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.AuthManager;
import com.tikiticket.core.api.impl.AuthManagerImpl;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.test.connector.HttpClientConnector;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by veinhorn on 28.3.17.
 */
public class AuthManagerTest {
    private Connector connector;
    private AuthManager authManager;

    @Before
    public void init() {
        connector = new HttpClientConnector(HttpClients.createDefault());
        authManager = new AuthManagerImpl(connector);
    }

    @Test
    public void testAuthManager() throws TikiTicketException {
        assertEquals("AuthManager should successfully perform auth", authManager.authenticate(), true);
    }
}
