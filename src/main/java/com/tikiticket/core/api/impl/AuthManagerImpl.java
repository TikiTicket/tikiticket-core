package com.tikiticket.core.api.impl;

import com.tikiticket.core.api.AuthManager;
import com.tikiticket.core.exception.TikiTicketException;

/**
 * Created by veinhorn on 28.3.17.
 */
public class AuthManagerImpl implements AuthManager {
    @Override
    public boolean authenticate() throws TikiTicketException {
        return true;
    }
}
