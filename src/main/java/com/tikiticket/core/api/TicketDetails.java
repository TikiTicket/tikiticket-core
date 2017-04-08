package com.tikiticket.core.api;

import java.util.Date;

/**
 * Created by veinhorn on 8.4.17.
 */
public interface TicketDetails {
    Long getOrderNumber();
    Date getOrderDate();
    String getTrainNumber();
    String getDispatchStation();
    String getArrivalStation();
    String getDispatchDate();
    Date getArrivalDate();
    String getWagonRoad();
    String getWagonOwner();
    Integer getWagonNumber();
    String getWagonType();
    String getSeats();
    Integer getNumberOfSeats();
    String getCost();
    String getStatus();
}
