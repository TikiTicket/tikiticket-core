package com.tikiticket.core;

import com.tikiticket.core.exception.TikiTicketException;

import java.util.Map;

/**
 * Created by veinhorn on 28.3.17.
 * Связующее звено между логикой ядра и HTTP клиентом
 */
public interface Connector extends Storage {
    Context doGet(String url) throws TikiTicketException;
    Context doPost(String url, Map<String, String> params) throws TikiTicketException;
    Credentials getCredentials();
}
