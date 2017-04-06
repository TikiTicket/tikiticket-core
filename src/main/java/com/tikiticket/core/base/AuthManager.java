package com.tikiticket.core.base;

import com.tikiticket.core.Connector;

/**
 * Created by veinhorn on 28.3.17.
 * Предоставляет реализацию прокси-мэнеджера для автоматической аутентификации
 * в случае простоя (истечения таймаута)
 */
abstract class AuthManager {
    protected AuthConnector connector;

    AuthManager(Connector connector) {
        this.connector = new AuthConnector(connector);
    }
}
