package com.tikiticket.core.base;

import com.tikiticket.core.Connector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 * Представляет из себя частичную реализацию коннектора с хранилищем в виде хэш мапы
 */
public abstract class BaseConnector implements Connector {
    protected Map<String, Object> storage;

    public BaseConnector() {
        storage = new HashMap<>();
    }

    @Override
    public Map<String, Object> getStorage() {
        return storage;
    }

    @Override
    public void updateStorage(String key, Object value) {
        storage.put(key, value);
    }
}
