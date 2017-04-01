package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.test.connector.HttpClientConnector;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by veinhorn on 31.3.17.
 */
public class ConnectorTest {
    private static final String LOGIN_PAGE_URL = "https://poezd.rw.by/wps/portal/home/login_main";

    private Connector connector;

    @Before
    public void init() {
        connector = new HttpClientConnector(HttpClients.createDefault());
    }

    @Ignore
    @Test
    public void testConnector() throws TikiTicketException {
        Context context = connector.doGet(LOGIN_PAGE_URL);
        String ok = "ok";
    }
}
