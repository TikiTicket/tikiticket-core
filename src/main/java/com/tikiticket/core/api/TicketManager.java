package com.tikiticket.core.api;

import com.tikiticket.core.exception.TikiTicketException;

import java.util.List;

/**
 * Created by veinhorn on 6.4.17.
 * Позволяет получать информацию по предстоящим поездкам, а также детальную
 * информацию по конкретным билетам
 */
// TODO: Обновить документацию
public interface TicketManager {
    /** Получение списка билетов по предстоящим поездкам */
    List<Ticket> getUpcomingTickets() throws TikiTicketException;
    /** Более быстрый метод получения детальной информации по билету,
     *  т.к заранее указывается категория для поиска */
    TicketDetails getTicketDetails(Long orderNumber, boolean isActive) throws TikiTicketException;
}
