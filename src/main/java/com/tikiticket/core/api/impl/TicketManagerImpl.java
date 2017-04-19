package com.tikiticket.core.api.impl;

import com.tikiticket.core.Connector;
import com.tikiticket.core.Context;
import com.tikiticket.core.Status;
import com.tikiticket.core.api.Ticket;
import com.tikiticket.core.api.TicketDetails;
import com.tikiticket.core.api.TicketManager;
import com.tikiticket.core.api.impl.parser.TicketDetailsParser;
import com.tikiticket.core.api.impl.parser.UpcomingTicketsParser;
import com.tikiticket.core.base.BaseManager;
import com.tikiticket.core.base.EventListener;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.javatuples.Pair;

import java.util.List;
import java.util.Map;

import static com.tikiticket.core.Constants.*;

/**
 * Created by veinhorn on 6.4.17.
 * Реализация менеджера билетов
 */
public class TicketManagerImpl extends BaseManager implements TicketManager {
    private static final String UPCOMING_TICKETS_PAGE_URL = "https://poezd.rw.by/wps/myportal/home/rp/private";

    public TicketManagerImpl(Connector connector, EventListener listener) {
        super(connector, listener);
    }

    // TODO: Реализовать работу метода при переходе из статуса COMPLETED_TRIPS
    /**
     * На данный момент работа этого метода не поддерживается из статуса COMPLETED_TICKETS
     * @return список предстоящих билетов
     * @throws TikiTicketException в случае ошибки
     */
    @Override
    public List<Ticket> getUpcomingTickets() throws TikiTicketException {
        if (connector.getStatus() == Status.COMPLETED_TICKETS) {
            throw new TikiTicketException("Unsupported from COMPLETED_TICKETS status", new Exception());
        } else {
            Context context = connector.toStatus(Status.UPCOMING_TICKETS).doGet(UPCOMING_TICKETS_PAGE_URL);
            Pair<List<Ticket>, Map<String, String>> data = new UpcomingTicketsParser().parse(context);
            // сохранение параметров формы для последующего использования
            connector.updateStorage(FORM_PARAMETERS, data.getValue1());

            return data.getValue0();
        }
    }

    // TODO: Сделать логи с переходами по статусам для коннектора, чтобы можно было отслеживать как работает
    // TODO: вся цепочка, должен где-то определяться некий глобальный листенер
    @Override
    public List<Ticket> getCompletedTickets() throws TikiTicketException {
        /** Если мы находимся в статусе COMPLETED_TICKETS или COMPLETED_TICKETS_LIST, то кидаем исключение,
         *  в последующем здесь должен быть реализован более интересный и оптимизированный алгоритм работы */
        /** TODO: Пока что здесь можно сделать реализацию - разлогиниться и повторно выполнить getCompletedTickets */
        if (connector.getStatus() == Status.COMPLETED_TICKETS && connector.getStatus() == Status.COMPLETED_TICKETS_LIST) {
            throw new TikiTicketException("", new Exception());
        } else { // иначе из любого другого статуса мы де
            Context context = connector.toStatus(Status.UPCOMING_TICKETS).doGet(UPCOMING_TICKETS_PAGE_URL);

            // get tabs_l2 class
            

            String ok = "ok";
            return null;
        }
    }

    /**
     * Выполняется быстрее из статуса Status.UPCOMING_TICKETS, иначе производится
     * дополнительное действие для получения списка билетов по предстоящим поездкам
     * На данный момент не поддерживается работа в случае если isActive равен false
     * @param orderNumber
     * @param isActive используется для указания типа билета (предстоящая или совершенная поездка)
     * @return подробную информацию по билету
     * @throws TikiTicketException в случае ошибки
     */
    @Override
    public TicketDetails getTicketDetails(Long orderNumber, boolean isActive) throws TikiTicketException {
        if (!isActive) throw new TikiTicketException("Unsupported when isActive=false", new Exception());

        /** В этом случае необходимо провести дополнительный запрос */
        if (connector.getStatus() != Status.UPCOMING_TICKETS) getUpcomingTickets();

        Map<String, String> formParams = (Map<String, String>) connector.getStorage().get(FORM_PARAMETERS);

        // TODO: Проследить почему данные формы null в этом случае
        /** Случается когда "Нет информации о заказах" */
        if (formParams == null) throw new TikiTicketException("Cannot get details data for order with id #" + orderNumber, new Exception());

        String ticketDetailsUrl = formParams.get("ordersFormActionUrl");
        formParams.remove("ordersFormActionUrl");

        String rowNum = countRowNumber(formParams, orderNumber);
        if ("-1".equals(rowNum)) throw new TikiTicketException("Cannot get details data for order with id #" + orderNumber, new Exception());
        formParams.put("rownum", rowNum);

        /** Тут нужно подправить параметр списка */
        String updatedListParam = formParams.get(FORM_LIST_PARAMETER).replaceFirst("cabOrderList1:[\\d]", "cabOrderList1:" + rowNum);
        formParams.put(FORM_LIST_PARAMETER, updatedListParam);

        Util.printMap(formParams);

        Context context = connector.toStatus(Status.UPCOMING_TICKET_DETAILS).doPost(ticketDetailsUrl, formParams);
        return new TicketDetailsParser().parse(context);
    }

    /** Получение номера строки на основе сохраненных данных формы (данные сохраняются на этапе
     *  парсинга странички) */
    private String countRowNumber(Map<String, String> formParameters, Long orderNumber) {
        String[] orderNumbers = formParameters.get(ORDER_NUMBERS).replaceAll("[\\[ \\]]", "").split(",");
        String rowNum = "-1"; // начальное значение, "неправильное"
        for (int i = 0; i < orderNumbers.length; i++)
            if (orderNumber.toString().equals(orderNumbers[i]))
                rowNum = Integer.valueOf(i).toString();
        formParameters.remove(ORDER_NUMBERS);
        return rowNum;
    }
}
