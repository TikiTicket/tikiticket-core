package com.tikiticket.core.test.parser;

import com.tikiticket.core.Context;
import com.tikiticket.core.api.Ticket;
import com.tikiticket.core.api.impl.parser.UpcomingTicketsParser;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.apache.commons.io.IOUtils;
import org.javatuples.Pair;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by veinhorn on 9.4.17.
 */
public class UpcomingTicketsParserTest {
    @Test
    public void testUpcomingTicketsParser() throws IOException, TikiTicketException {
        URL resource = this.getClass().getResource("/html/1_my_ticket.html");

        String html = IOUtils.toString(resource, Charset.defaultCharset());
        Context context = Util.newContext(html, null);
        Pair<List<Ticket>, Map<String, String>> ticketsData = new UpcomingTicketsParser().parse(context);
        String ok = "ok";
    }
}
