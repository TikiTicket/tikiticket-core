package com.tikiticket.core.base;

import com.tikiticket.core.Status;

/**
 * Created by veinhorn on 18.4.17.
 * Позволяет вызывать каллбэки на определенные события в умном коннекторе
 */
public interface EventListener {
    void onStatusChanged(Status status);
}
