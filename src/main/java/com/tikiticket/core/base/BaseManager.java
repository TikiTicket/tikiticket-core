package com.tikiticket.core.base;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Status;

/**
 * Created by veinhorn on 28.3.17.
 *
 */
public abstract class BaseManager extends AuthManager {
    protected CleverConnector connector;

    public BaseManager(Connector connector) {
        super(connector);
        this.connector = new CleverConnector(super.connector, null);
        this.connector.toStatus(Status.UNDEFINED);
    }

    public BaseManager(Connector connector, EventListener listener) {
        super(connector);
        this.connector = new CleverConnector(super.connector, listener);
        this.connector.toStatus(Status.UNDEFINED);
    }
}
