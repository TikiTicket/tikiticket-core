package com.tikiticket.core.api;

import com.tikiticket.core.Connector;
import com.tikiticket.core.api.impl.AuthManagerImpl;
import com.tikiticket.core.api.impl.ProfileManagerImpl;
import com.tikiticket.core.api.impl.TicketManagerImpl;
import com.tikiticket.core.base.EventListener;

/**
 * Created by veinhorn on 1.4.17.
 */
// TODO: Улучшить логику создания менеджеров
// TODO: В конструкторе должен передаваться некий листенер с определенными методами
// TODO: Добавить listener во все остальные менеджеры
public class ManagerFactory {
    private Connector connector;
    private EventListener listener;

    public ManagerFactory(Connector connector) {
        this.connector = connector;
    }

    public ManagerFactory(Connector connector, EventListener listener) {
        this(connector);
        this.listener = listener;
    }

    public AuthManager newAuthManager() {
        return new AuthManagerImpl(connector);
    }

    public ProfileManager newProfileManager() {
        return new ProfileManagerImpl(connector);
    }

    public TicketManager newTicketManager() {
        return new TicketManagerImpl(connector, listener);
    }
}
