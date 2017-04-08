package com.tikiticket.core.test.parser;

import com.tikiticket.core.Context;
import com.tikiticket.core.api.TicketDetails;
import com.tikiticket.core.api.impl.parser.TicketDetailsParser;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by veinhorn on 8.4.17.
 */
// TODO: Доделать тест, вынести логику загрузки ресурсов в отдельный класс
public class TicketDetailsParserTest {
    @Test
    @Ignore
    public void testTicketDetailsParser() throws IOException, TikiTicketException {
        URL resource = this.getClass().getResource("/html/ticket_details.html");

        String html = IOUtils.toString(resource, Charset.defaultCharset());
        Context context = Util.newContext(html, null);
        TicketDetails ticketDetails = new TicketDetailsParser().parse(context);
        String ok = "ok";
    }
}
