package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Creates bluetooth socket and connects on it.
 * 3.2.1.2.1. Connect Device Successfully
 * 3.2.1.2.3. Handle Failed Attempt to Connect to Computer Via Bluetooth
 * Created by Brendan on 3/13/2016.
 */
public class InitialConnectionTranscever {

    public static final UUID BB_REMOTE_UUID = UUID.fromString("223B9890-02FF-44B5-AD44-9CF50CB3BCE6"); // auto-generated uuid

    public static BluetoothSocket globalSocket;

    public static void connect(BluetoothDevice device) throws IOException {
        if( ConnectionState.isConnected() ) {
            globalSocket.close();
            ConnectionState.setConnected(false);
        }
        globalSocket = device.createRfcommSocketToServiceRecord(BB_REMOTE_UUID);
        globalSocket.connect();
    }

    public static void close() throws IOException {
        globalSocket.close();
    }
}
