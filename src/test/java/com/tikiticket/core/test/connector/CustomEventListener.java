package com.tikiticket.core.test.connector;

import com.tikiticket.core.Status;
import com.tikiticket.core.base.EventListener;

/**
 * Created by veinhorn on 18.4.17.
 */
public class CustomEventListener implements EventListener {
    @Override
    public void onStatusChanged(Status status) {
        System.out.println("Status changed to: " + status.toString());
    }
}
