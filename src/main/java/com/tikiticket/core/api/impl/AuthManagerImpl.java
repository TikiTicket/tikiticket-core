package com.tikiticket.core.api.impl;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.AuthManager;
import com.tikiticket.core.base.BaseManager;
import com.tikiticket.core.exception.TikiTicketException;

/**
 * Created by veinhorn on 28.3.17.
 */
public class AuthManagerImpl extends BaseManager implements AuthManager {
    public AuthManagerImpl(Connector connector) {
        super(connector);
    }

    @Override
    public boolean authenticate() throws TikiTicketException {
        connector.doGet(null);
        return connector.isAuthenticated();
    }
}
