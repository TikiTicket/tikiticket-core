package com.tikiticket.core.test;

import com.tikiticket.core.api.Ticket;
import com.tikiticket.core.api.TicketDetails;
import com.tikiticket.core.api.TicketManager;
import com.tikiticket.core.api.impl.TicketManagerImpl;
import com.tikiticket.core.exception.TikiTicketException;
import org.junit.Test;

import java.util.List;

/**
 * Created by veinhorn on 6.4.17.
 */
public class TicketManagerTest extends BaseTest {
    @Test
    public void testTicketManager() throws TikiTicketException {
        TicketManager ticketManager = new TicketManagerImpl(connector);
        List<Ticket> tickets = ticketManager.getUpcomingTickets();

        TicketDetails details1 = ticketManager.getTicketDetails(72675891804531L, true);
        TicketDetails details2 = ticketManager.getTicketDetails(72675891510833L, true);
        TicketDetails details3 = ticketManager.getTicketDetails(72675891873175L, true);

        String ok = "ok";
    }
}
