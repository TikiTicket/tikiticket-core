package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.test.connector.HttpClientConnector;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;

/**
 * Created by veinhorn on 6.4.17.
 * Упрощает создание тестов
 */
public class BaseTest {
    protected Connector connector;

    @Before
    public void init() {
        connector = new HttpClientConnector(HttpClients.createDefault());
    }
}
