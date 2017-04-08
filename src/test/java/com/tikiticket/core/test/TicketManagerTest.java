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
        TicketDetails ticketDetails = ticketManager.getTicketDetails(72475891090973L, true);
        String ok = "ok";
    }
}
