package com.tikiticket.core;

import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 * Абстракция хранилища данных для Connector'а. В качестве хранилища используется map
 */
public interface Storage {
    Map<String, Object> getStorage();
    void updateStorage(String key, Object value);
}
