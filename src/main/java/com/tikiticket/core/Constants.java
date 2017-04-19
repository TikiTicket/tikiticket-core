package com.tikiticket.core;

/**
 * Created by veinhorn on 31.3.17.
 */
public class Constants {
    public static final String BASE_URL = "https://poezd.rw.by";

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    /** Формат даты, использующийся на сайте */
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    /** --- Заголовки для хранилища --- */

    /** Время последней успешной аутентификации пользователя */
    public static final String AUTH_TIME = "auth_time";

    /** Текущий статус коннектора */
    public static final String STATUS = "status";

    /** Используется для сохранения последней загруженной странички в хранилище коннектора */
    public static final String LAST_HTML = "last_html";

    /** Используется для хранения в хранилище коннектора данных,
     *  специфичных для формы на сайте */
    public static final String FORM_PARAMETERS = "form_parameters";



    /** --- */

    /** Параметры, которые хранятся в form_parameters в процессе оплаты */
    public static final String ORDER_NUMBERS = "order_numbers";
    /** --- */

    /** Параметры формы */
    public static final String FORM_LIST_PARAMETER = "viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:_idcl";
    /** --- */
}
