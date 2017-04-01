package com.tikiticket.core.base;

import com.tikiticket.core.Connector;

/**
 * Created by veinhorn on 28.3.17.
 * Предоставляет реализацию прокси-мэнеджера для автоматической аутентификации
 * в случае простоя (истечения таймаута)
 */
public abstract class AuthManager {
    protected Connector connector;

    public AuthManager(Connector connector) {
        this.connector = new AuthConnector(connector);
    }
}
