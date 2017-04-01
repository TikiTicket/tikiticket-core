package com.tikiticket.core;

/**
 * Created by veinhorn on 28.3.17.
 * Предоставляет данные для аутентификации пользователя
 */
public interface Credentials {
    String getLogin();
    String getPassword();
}
