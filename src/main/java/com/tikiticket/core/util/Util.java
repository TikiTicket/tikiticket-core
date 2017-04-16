package com.tikiticket.core.util;

import com.tikiticket.core.Context;
import com.tikiticket.core.Credentials;
import com.tikiticket.core.api.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tikiticket.core.Constants.BASE_URL;

/**
 * Created by veinhorn on 28.3.17.
 */
public class Util {
    public static Context newContext(final String html, final Map<String, String> headers) {
        return new Context() {
            @Override
            public String getHtml() {
                return html;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
    }

    /** Создание ссылки на базе относительной */
    public static String fromRelativeUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static Credentials newCredentials(final String login, final String password) {
        return new Credentials() {
            @Override
            public String getLogin() {
                return login;
            }

            @Override
            public String getPassword() {
                return password;
            }
        };
    }

    /** Получение списка с номерами заказов из списка билетов */
    public static List<Long> getOrderNumbers(List<Ticket> tickets) {
        List<Long> orderNumbers = new ArrayList<>();
        for (Ticket ticket : tickets) orderNumbers.add(ticket.getOrderNumber());
        return orderNumbers;
    }

    /** Используется для вывода параметров в удобочитаемом формате */
    public static void printMap(Map<String, String> map) {
        for (String key : map.keySet()) System.out.println(key + ": " + map.get(key));
        System.out.println();
    }
}
