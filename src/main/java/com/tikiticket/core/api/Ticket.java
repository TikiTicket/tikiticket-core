package com.tikiticket.core.api;

import java.util.Date;

/**
 * Created by veinhorn on 6.4.17.
 */
public interface Ticket {
    Long getOrderNumber();
    Date getOrderDate();
    Date getDispatchDate();
    String getDispatchStation();
    String getArrivalStation();
    String getTrainNumber();
    Integer getNumberOfSeats();
    String getCost();
}
