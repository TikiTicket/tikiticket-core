package com.tikiticket.core;

import com.tikiticket.core.exception.TikiTicketException;

/**
 * Created by veinhorn on 31.3.17.
 */
public interface PageParser<T> {
    T parse(Context context) throws TikiTicketException;
}
