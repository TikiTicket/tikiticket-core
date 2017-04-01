package com.tikiticket.core.base;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.Credentials;
import com.tikiticket.core.PageParser;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

import static com.tikiticket.core.Constants.*;

/**
 * Created by veinhorn on 28.3.17.
 * Инкапсулирует логику аутентификации
 */
public class Authenticator {
    private static final String LOGIN_PAGE_URL = "https://poezd.rw.by/wps/portal/home/login_main";
    private static final String BUY_URL = "https://poezd.rw.by/wps/myportal/home/rp/buyTicket";

    /** Таймаут в секундах, после которого сервер перестает принимать пользователя
     *  за аутентифицированного, на rw.by примерно 120 секунд */
    private static final Long TIMEOUT = 120L;

    private Connector connector;
    private Long timeout;

    public Authenticator(Connector connector) {
        this.connector = connector;
        timeout = TIMEOUT;
    }

    public Authenticator(Connector connector, Long timeout) {
        this.connector = connector;
        this.timeout = timeout;
    }

    /**
     * Производит попутку аутентификации пользователя
     * @return true если аутентификация прошла успешно, иначе false
     * @throws TikiTicketException в случае ошибки в процессе аутентификации
     */
    public Pair<Boolean, Context> authenticate() throws TikiTicketException {
        // Pair<Boolean, Boolean> isAuthed = isAuthenticatedInner();
        if (!isAuthenticated()) {
        // if (!isAuthed.getValue0()) {
            // Context loginCtx = isAuthed.getValue1() ? singleLoginPage() : doubleLoginPage();
            Context loginCtx = doubleLoginPage();

            String authUrl = new LoginPageParser().parse(loginCtx);
            Context authCtx = connector.doPost(authUrl, toParams(connector.getCredentials()));

            if (authenticate(authCtx)) {
                updateLastAuthTime();
                return new Pair<>(true, authCtx);
            } else {
                return new Pair<>(false, authCtx);
            }
        }
        return new Pair<>(true, null);
    }

    /**
     *  Проверка аутентификации пользователя по некоторым критериям
     *  (совпадение Location ссылки и пустое тело ответа)
     * @param ctx
     * @return true в случае совпадения по критериям
     */
    private boolean authenticate(Context ctx) {
        if ("".equals(ctx.getHtml())) {
            Map<String, String> headers = ctx.getHeaders();
            if (headers.containsKey("Location") && headers.get("Location").equals(BUY_URL)) return true;
        }
        return false;
    }

    /** Проверяет аутентифицирован ли пользователь на основе
     *  времени последней успешной аутентификации в коннекторе */
    public boolean isAuthenticated() {
        Long authTime = (Long) connector.getStorage().get(AUTH_TIME);
        return authTime != null && !isTimeoutExpired(authTime);
    }

    /** Проверяет истек ли таймаут аутентификации */
    private boolean isTimeoutExpired(Long authTime) {
        return (System.currentTimeMillis() - authTime) / 1000 > timeout;
    }

    /** Приходится дублировать запрос на страницу с логином, т.к в случае если таймаут
     *  в аутентификаторе истек, а на сайте пользователь попрежднему аутентифицирован,
     *  нужно 2 запроса чтобы попасть на валидную страничку с формой для аутентификации */
    private Context doubleLoginPage() throws TikiTicketException {
        Context ctx = connector.doGet(LOGIN_PAGE_URL);
        return connector.doGet(LOGIN_PAGE_URL);
    }

    private Context singleLoginPage() throws TikiTicketException {
        return connector.doGet(LOGIN_PAGE_URL);
    }

    private Map<String, String> toParams(Credentials credentials) {
        Map<String, String> params = new HashMap<>();
        params.put(LOGIN, credentials.getLogin());
        params.put(PASSWORD, credentials.getPassword());
        return params;
    }

    /** Обновление времени последней успешной аутентификации */
    private void updateLastAuthTime() {
        connector.updateStorage(AUTH_TIME, System.currentTimeMillis());
    }

    /** Парсер получения ссылки для аутентификации */
    private class LoginPageParser implements PageParser<String> {
        public String parse(Context ctx) throws TikiTicketException {
            try {
                return parse(Jsoup.parse(ctx.getHtml()));
            } catch (Exception e) {
                throw new TikiTicketException("Cannot fetch auth url on login page", e);
            }
        }

        private String parse(Document document) {
            return Util.fromRelativeUrl(document.getElementById("login").attr("action"));
        }
    }

    // TODO: Доработать этот метод в следующих версиях
    /** Пока не проработанная оптимизированная версия проверки аутентификации, позволяет оптимизировать кол-во запросов
     *  т.к как в настоящий момент используется двойной запрос на страницу с формой для ввода логина и пароля
     *  false, true  - пользователь не аутонтифицирован и пытается аутентифицироваться в первый раз
     *  true, false  - пользователь аутентифицирован
     *  false, false - пользователь не аутофинтицирован и пытается аутентифицироваться повторно
     * */
    /*private Pair<Boolean, Boolean> isAuthenticatedInner() {
        Map<String, Object> storage = connector.getStorage();
        Long authTime = (Long) storage.get(AUTH_TIME);
        *//** Время последней аутентификации должно укладываться во временной промежуток *//*
        if (authTime == null) return new Pair<>(false, true);
        else {
            if (isTimeoutExpired(authTime)) return new Pair<>(false, false);
            else return new Pair<>(true, false);
        }
    }*/
}
