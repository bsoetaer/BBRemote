package com.example.bbschool.bbremotemobile;

/**
 * To keep track of the connection state of the android device.
 * Used for handling disconnects and for displaying proper menus based on connection status.
 * 3.2.1.2.6. Connection Lost
 * 3.2.2.2.2. Mode Select While Disconnected
 * Created by Braeden on 3/25/2016.
 */
public final class ConnectionState {

    private static Boolean connected = false;
    private static final Object connectedLock = new Object();
    private ConnectionState() {}

    public static boolean isConnected() { return connected; }

    public static void setConnected(boolean state) {
        synchronized (connectedLock) {
            connected = state;
        }
    }
}
