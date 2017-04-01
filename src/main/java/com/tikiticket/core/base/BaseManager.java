package com.tikiticket.core.base;

import com.tikiticket.core.Connector;

/**
 * Created by veinhorn on 28.3.17.
 */
public abstract class BaseManager extends AuthManager {
    public BaseManager(Connector connector) {
        super(connector);
    }
}
