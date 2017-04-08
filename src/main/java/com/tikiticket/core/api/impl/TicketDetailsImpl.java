package com.tikiticket.core.api.impl;

import com.tikiticket.core.api.TicketDetails;

import java.util.Date;

/**
 * Created by veinhorn on 8.4.17.
 */
public class TicketDetailsImpl implements TicketDetails {
    private Long orderNumber;
    private Date orderDate;
    private String trainNumber;
    private String dispatchStation;
    private String arrivalStation;
    private String dispatchDate;
    private Date arrivalDate;
    private String wagonRoad;
    private String wagonOwner;
    private Integer wagonNumber;
    private String wagonType;
    private Integer numberOfSeats;
    private String seats;
    private String cost;
    private String status;

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
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
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
    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    @Override
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String getWagonRoad() {
        return wagonRoad;
    }

    public void setWagonRoad(String wagonRoad) {
        this.wagonRoad = wagonRoad;
    }

    @Override
    public String getWagonOwner() {
        return wagonOwner;
    }

    public void setWagonOwner(String wagonOwner) {
        this.wagonOwner = wagonOwner;
    }

    @Override
    public Integer getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(Integer wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    @Override
    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    @Override
    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    @Override
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
