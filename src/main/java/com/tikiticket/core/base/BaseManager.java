package com.tikiticket.core.base;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Status;

/**
 * Created by veinhorn on 28.3.17.
 */
public abstract class BaseManager extends AuthManager {
    protected CleverConnector connector;

    public BaseManager(Connector connector) {
        super(connector);
        this.connector = new CleverConnector(super.connector);
        this.connector.toStatus(Status.UNDEFINED);
    }
}
