package com.tikiticket.core.api;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.impl.AuthManagerImpl;
import com.tikiticket.core.api.impl.ProfileManagerImpl;

/**
 * Created by veinhorn on 1.4.17.
 */
// TODO: Улучшить логику создания менеджеров
public class ManagerFactory {
    private Connector connector;

    public ManagerFactory(Connector connector) {
        this.connector = connector;
    }

    public AuthManager newAuthManager() {
        return new AuthManagerImpl(connector);
    }

    public ProfileManager newProfileManager() {
        return new ProfileManagerImpl(connector);
    }
}
