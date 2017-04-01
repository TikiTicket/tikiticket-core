package com.tikiticket.core.test;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.Credentials;
import com.tikiticket.core.base.Authenticator;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.test.connector.HttpClientConnector;
import com.tikiticket.core.util.Util;
import org.apache.http.impl.client.HttpClients;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.tikiticket.core.Constants.AUTH_TIME;
import static org.junit.Assert.*;

/**
 * Created by veinhorn on 31.3.17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticatorTest {
    private Connector connector;
    private Authenticator authenticator;

    @Before
    public void init() {
        initialize();
    }

    /** Тест одиночной аутентификации */
    @Test
    public void testAuthenticator1() throws TikiTicketException {
        Pair<Boolean, Context> authData = authenticator.authenticate();

        /** Проверка того что пользователь успешно аутентифицировался */
        assertEquals(authData.getValue0(), true);
        assertEquals(authenticator.isAuthenticated(), true);
        /** Контекст не должен быть равен null после первой аутентификации */
        assertNotNull("Context should not be null after first auth", authData.getValue1());
        /** В хранилище коннектора должно быть время последней успешной аутентификации */
        assertEquals(connector.getStorage().containsKey(AUTH_TIME), true);
    }

    /** Тест повторной аутентификации после уже осуществленной успешной аутентификации */
    @Test
    public void testAuthenticator2() throws TikiTicketException {
        initialize(); // нужна переинициализация коннектора и аутентификатора

        Pair<Boolean, Context> authData = authenticator.authenticate();
        Long authTime = (Long) connector.getStorage().get(AUTH_TIME);
        Pair<Boolean, Context> authData2 = authenticator.authenticate();

        /** Проверка того что пользователь успешно аутентифицировался */
        assertEquals(authData2.getValue0(), true);
        assertEquals(authenticator.isAuthenticated(), true);
        /** Контекст должен быть равен null после второй успешной аутентификации */
        assertNull("Context should be null after second auth", authData2.getValue1());
        /** В хранилище коннектора должно быть время последней успешной аутентификации */
        assertEquals(connector.getStorage().containsKey(AUTH_TIME), true);
        /** Время после первой и второй успешной аутентификации должно совпадать
         *  (в случае если мы укладываемся в таймаут) */
        assertEquals(authTime, (Long) connector.getStorage().get(AUTH_TIME));
    }

    /** Тест аутентификации в случае неправильных логина или пароля */
    @Test
    public void testAuthenticator3() throws TikiTicketException {
        initialize(Util.newCredentials("test1", "testpass")); // нужна переинициализация коннектора и аутентификатора

        Pair<Boolean, Context> authData = authenticator.authenticate();

        assertEquals(authData.getValue0(), false);
        assertEquals(authenticator.isAuthenticated(), false);
        assertNotNull("Context should not be null after failed auth", authData.getValue1());
        assertEquals(connector.getStorage().containsKey(AUTH_TIME), false);
    }

    /** Тест аутентификации в случае простоя по таймауту */
    @Test
    public void testAuthenticator4() throws TikiTicketException, InterruptedException {
        initialize(2L);

        Pair<Boolean, Context> authData = authenticator.authenticate();
        Long authTime = (Long) connector.getStorage().get(AUTH_TIME);

        assertEquals(connector.getStorage().containsKey(AUTH_TIME), true);
        assertEquals(authenticator.isAuthenticated(), true);

        Thread.sleep(3000);

        assertEquals(authenticator.isAuthenticated(), false);

        Pair<Boolean, Context> authData2 = authenticator.authenticate();

        assertEquals(authenticator.isAuthenticated(), true);
        assertNotEquals(authTime, (Long) connector.getStorage().get(AUTH_TIME));
    }

    private void initialize() {
        connector = new HttpClientConnector(HttpClients.createDefault());
        authenticator = new Authenticator(connector);
    }

    private void initialize(Credentials credentials) {
        connector = new HttpClientConnector(HttpClients.createDefault(), credentials);
        authenticator = new Authenticator(connector);
    }

    /** timeout in seconds */
    private void initialize(Long timeout) {
        connector = new HttpClientConnector(HttpClients.createDefault());
        authenticator = new Authenticator(connector, timeout);
    }
}
