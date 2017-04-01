package com.tikiticket.core;

import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 * Хранит состояние, в котором находится Connector и содержит в себе такие
 * данные как последняя загруженная страница, последние хэдеры
 */
public interface Context {
    String getHtml();
    Map<String, String> getHeaders();
}
