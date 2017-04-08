package com.tikiticket.core.api.impl;

import com.tikiticket.core.api.Ticket;

import java.util.Date;

/**
 * Created by veinhorn on 7.4.17.
 */
public class TicketImpl implements Ticket {
    private Long orderNumber;
    private Date orderDate;
    private Date dispatchDate;
    private String dispatchStation;
    private String arrivalStation;
    private String trainNumber;
    private Integer numberOfSeats;
    private String cost;

    @Override
    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    @Override
    public String getDispatchStation() {
        return dispatchStation;
    }

    public void setDispatchStation(String dispatchStation) {
        this.dispatchStation = dispatchStation;
    }

    @Override
    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    @Override
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Override
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
