package com.tikiticket.core.api.impl.parser;

import com.tikiticket.core.Constants;
import com.tikiticket.core.Context;
import com.tikiticket.core.PageParser;
import com.tikiticket.core.api.TicketDetails;
import com.tikiticket.core.api.impl.TicketDetailsImpl;
import com.tikiticket.core.exception.TikiTicketException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;

/**
 * Created by veinhorn on 8.4.17.
 * Парсинг подробной информации по билету
 */
public class TicketDetailsParser implements PageParser<TicketDetails> {
    @Override
    public TicketDetails parse(Context context) throws TikiTicketException {
        try {
            return parse(Jsoup.parse(context.getHtml()));
        } catch (Exception e) {
            throw new TikiTicketException("Cannot fetch ticket details data", e);
        }
    }

    private TicketDetails parse(Document document) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Elements tableRows = document.getElementsByClass("fields").get(0).getElementsByTag("tr");

        TicketDetailsImpl details = new TicketDetailsImpl();
        details.setOrderNumber(Long.parseLong(tableRows.get(0).child(1).text()));
        details.setOrderDate(dateFormat.parse(tableRows.get(1).child(1).text()));
        details.setTrainNumber(tableRows.get(2).child(1).text());
        details.setDispatchStation(tableRows.get(3).child(1).text());
        details.setArrivalStation(tableRows.get(4).child(1).text());
        details.setDispatchDate(tableRows.get(5).child(1).text());
        details.setArrivalDate(dateFormat.parse(tableRows.get(6).child(1).text()));
        details.setWagonRoad(tableRows.get(7).child(1).text());
        details.setWagonOwner(tableRows.get(8).child(1).text());
        details.setWagonNumber(Integer.parseInt(tableRows.get(9).child(1).text()));
        details.setWagonType(tableRows.get(10).child(1).text());
        details.setNumberOfSeats(Integer.parseInt(tableRows.get(11).child(1).text()));
        details.setSeats(tableRows.get(12).child(1).text());
        details.setCost(tableRows.get(14).child(1).text());
        details.setStatus(tableRows.get(15).child(1).text());
        return details;
    }
}
