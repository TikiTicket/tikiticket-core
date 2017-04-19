package com.tikiticket.core.base;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.Credentials;
import com.tikiticket.core.exception.TikiTicketException;
import org.javatuples.Pair;

import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 * Реализация Connector'а, которая проксирует запросы на сервер и в случае если
 * timeout истек, проводит повторную аутентификацию пользователя
 */
// TODO: Реализовать возможность выхода для аутентифицированного пользователя
public class AuthConnector implements Connector {
    private Connector connector;
    private Authenticator authenticator;

    public AuthConnector(Connector connector) {
        this.connector = connector;
        authenticator = new Authenticator(connector);
    }

    @Override
    public Context doGet(String url) throws TikiTicketException {
        Pair<Boolean, Context> authData = authenticator.authenticate();
        checkAuthentication(authData);
        return url == null ? authData.getValue1() : connector.doGet(url);
    }

    @Override
    public Context doPost(String url, Map<String, String> params) throws TikiTicketException {
        Pair<Boolean, Context> authData = authenticator.authenticate();
        checkAuthentication(authData);
        return url == null ? authData.getValue1() : connector.doPost(url, params);
    }

    @Override
    public Map<String, Object> getStorage() {
        return connector.getStorage();
    }

    @Override
    public void updateStorage(String key, Object value) {
        connector.updateStorage(key, value);
    }

    @Override
    public Credentials getCredentials() {
        return connector.getCredentials();
    }

    /** Проверяет аутентифицирован ли пользователь, делегируя запрос аутентификатору */
    public boolean isAuthenticated() {
        return authenticator.isAuthenticated();
    }

    private void checkAuthentication(Pair<Boolean, Context> authData) throws TikiTicketException {
        if (!authData.getValue0())
            throw new TikiTicketException("Cannot authenticate user with login/pass: " + formatOutput(), new Exception());
    }

    private String formatOutput() {
        Credentials creds = getCredentials();
        return creds.getLogin() + ", " + creds.getPassword();
    }
}
