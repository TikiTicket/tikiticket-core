package com.tikiticket.core.api.impl.parser;

import com.tikiticket.core.Constants;
import com.tikiticket.core.Context;
import com.tikiticket.core.PageParser;
import com.tikiticket.core.api.Ticket;
import com.tikiticket.core.api.impl.TicketImpl;
import com.tikiticket.core.exception.TikiTicketException;
import com.tikiticket.core.util.Util;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by veinhorn on 8.4.17.
 * Парсинг предстоящих поездок и сохранение определенных данных формы заказов
 */
public class UpcomingTicketsParser implements PageParser<Pair<List<Ticket>, Map<String, String>>> {
    private static final String NO_TICKETS_MESSAGE = "Нет информации о заказах";

    // TODO: Может быть добавить обработку исключений здесь
    @Override
    public Pair<List<Ticket>, Map<String, String>> parse(Context context) throws TikiTicketException {
        return parse(Jsoup.parse(context.getHtml()));
    }

    /** Должно бросить исключение в случае если такой таблицы нет на странице */
    private Pair<List<Ticket>, Map<String, String>> parse(Document document) throws TikiTicketException {
        Elements informationTable = document.getElementsByClass("information");
        /** Если табличка есть: нужно парсить */
        if (!informationTable.isEmpty()) {
            Elements ticketRows = informationTable.get(0).getElementsByClass("rowClass1");
            ticketRows.addAll(informationTable.get(0).getElementsByClass("grey"));
            List<Ticket> tickets = parse(ticketRows);

            /** Подготовка данных формы и занесение их в map (используются в последующем для
             *  получения детальной информации по билету) */
            Map<String, String> params = new HashMap<>();

            params.put("viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:cabOrderList1:pagerWeb1__pagerWeb", "0");
            params.put("viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:selForNewOrderId1", "");
            params.put("viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:selForNewOrderDate1", "");
            params.put("viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2", "viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2");
            params.put("viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:_idcl", "viewns_7_48QFVAUK6PT510AGU3KRAG1004_:form2:cabOrderList1:0:_id71");

            String ids = document.getElementById("com.sun.faces.VIEW").val();
            String relativeUrl = Util.fromRelativeUrl(document.getElementsByTag("form").get(1).attr("action"));

            params.put("com.sun.faces.VIEW", ids);
            params.put("ordersFormActionUrl", relativeUrl);

            /** Записываем список заказов в карту, для последующего использования */
            params.put("order_numbers", Util.getOrderNumbers(tickets).toString());

            return new Pair<>(tickets, params);
        } else {
            /** Нужно проверить есть ли сообщение "Нет информации о заказах" */
            Elements errDiv = document.getElementsByClass("errDiv");
            if (!errDiv.isEmpty() && NO_TICKETS_MESSAGE.equals(errDiv.text())) {
                return new Pair<List<Ticket>, Map<String, String>>(new ArrayList<Ticket>(), null);
            }
            /** Иначе бросает исключение что мол не можем распарсить билеты */
            throw new TikiTicketException("Cannot parse upcoming tickets (there isn't table with class information)", new Exception());
        }
    }

    private List<Ticket> parse(Elements tableRows) throws TikiTicketException {
        List<Ticket> tickets = new ArrayList<>();
        for (Element row : tableRows) {
            TicketImpl ticket = new TicketImpl();
            try {
                ticket.setOrderNumber(Long.parseLong(row.child(0).text()));
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
                ticket.setOrderDate(dateFormat.parse(row.child(1).text()));
                ticket.setDispatchDate(dateFormat.parse(row.child(2).text()));
                ticket.setDispatchStation(row.child(3).text());
                ticket.setArrivalStation(row.child(4).text());
                ticket.setTrainNumber(row.child(5).text());
                ticket.setNumberOfSeats(Integer.parseInt(row.child(6).text()));
                ticket.setCost(row.child(7).text());
            } catch (Exception e) {
                throw new TikiTicketException("Incorrect type conversion or incorrect order", e);
            }
            tickets.add(ticket);
        }
        return tickets;
    }
}
