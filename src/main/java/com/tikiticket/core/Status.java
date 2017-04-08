package com.tikiticket.core;

/**
 * Created by veinhorn on 7.4.17.
 * Концепция статусов позволяет оптимизировать количество запросов и ускорить
 * получение информации в некоторых частях tikiticket-core API
 */
public enum Status {
    /** Этим статусом устанавливается при создании BaseManager (начальный статус) */
    UNDEFINED(-1),
    /** Список предстоящих поездок */
    UPCOMING_TICKETS(1),
    /** Страница с детальной информацией по билету */
    UPCOMING_TICKET_DETAILS(2),
    /** Страница с совершенными поездками */
    COMPLETED_TICKETS(3);

    // TODO: При добавлении новых статусов необходимо обновить метод #from

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /** Создает объект статуса на основе целочисленной константы */
    public static Status from(int status) {
        switch (status) {
            case 1:  return UPCOMING_TICKETS;
            case 2:  return UPCOMING_TICKET_DETAILS;
            case 3:  return COMPLETED_TICKETS;
            default: return UNDEFINED;
        }
    }
}
