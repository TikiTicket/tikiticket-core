package com.tikiticket.core.base;

import com.tikiticket.core.*;
import com.tikiticket.core.exception.TikiTicketException;

import java.util.Map;

import static com.tikiticket.core.Constants.STATUS;

/**
 * Created by veinhorn on 7.4.17.
 * Предоставляет концепцию статусов, возможно будет сохранять последнюю загруженную страницу
 * Позволяет:
 *   1. Автоматически сохранять последнюю загруженную страничку
 *   2. Упрощает обновение статусов
 *   3. Позволяет отслеживать события определенного рода, происходящие внутри коннектора
 */
// TODO: Написать юнит тесты к этому классу
// TODO: Переименовать, т.к не совсем отражает суть
public class CleverConnector implements Connector, Authentication {
    private AuthConnector connector;
    private EventListener listener;

    public CleverConnector(AuthConnector connector, EventListener listener) {
        this.connector = connector;
        this.listener = listener;
    }

    /** Обновляем в хранилище последнюю загруженную страничку */
    @Override
    public Context doGet(String url) throws TikiTicketException {
        Context context = connector.doGet(url);
        updateStorage(Constants.LAST_HTML, context.getHtml());
        return context;
    }

    @Override
    public Context doPost(String url, Map<String, String> params) throws TikiTicketException {
        Context context = connector.doPost(url, params);
        updateStorage(Constants.LAST_HTML, context.getHtml());
        return context;
    }

    @Override
    public Credentials getCredentials() {
        return connector.getCredentials();
    }

    @Override
    public void updateStorage(String key, Object value) {
        connector.updateStorage(key, value);
    }

    @Override
    public Map<String, Object> getStorage() {
        return connector.getStorage();
    }

    @Override
    public boolean isAuthenticated() {
        return connector.isAuthenticated();
    }

    /** Обновление статуса. Вызывается перед doget/dopost */
    public CleverConnector toStatus(Status status) {
        updateStorage(Constants.STATUS, status.value());
        if (listener != null) listener.onStatusChanged(status);
        return this;
    }

    /** Возвращает текущий статус коннектора, если в хранилище нет статуса, возвращает
     *  Status.UNDEFINED */
    public Status getStatus() {
        if (getStorage().containsKey(STATUS)) return Status.from((int) getStorage().get(STATUS));
        return Status.UNDEFINED;
    }
}
