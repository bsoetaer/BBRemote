package com.example.bbschool.bbremotemobile;

import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 * Created by Braeden on 3/25/2016.
 */
public final class ConnectionState {

    // TODO Reset this default to false. Set to true now for testing/navigation purposes
    private static Boolean connected = true;
    private static final Object connectedLock = new Object();
    private ConnectionState() {}

    public static boolean isConnected() { return connected; }

    public static void setConnected(boolean state) {
        synchronized (connectedLock) {
            connected = state;
        }
    }
}
