package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Brendan on 3/13/2016.
 */
public class InitialConnectionTranscever {

    public static final UUID BB_REMOTE_UUID = UUID.fromString("223B9890-02FF-44B5-AD44-9CF50CB3BCE6"); // auto-generated uuid

    public static BluetoothSocket globalSocket;

    public static void connect(BluetoothDevice device) throws IOException {
        globalSocket = device.createRfcommSocketToServiceRecord(BB_REMOTE_UUID);
        globalSocket.connect();
    }

    public static void close() throws IOException {
        globalSocket.close();
    }
}