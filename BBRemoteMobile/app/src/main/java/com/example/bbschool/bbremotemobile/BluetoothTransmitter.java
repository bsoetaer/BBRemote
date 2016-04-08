package com.example.bbschool.bbremotemobile;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Base transmitter class for sending data across the interface to the Windows computer. Required
 * by all requirements that send data.
 * 3.2.1.2.5. Send Data from Mobile Client to Desktop Client
 * 3.2.3.2.1. Send Key Input
 * 3.2.3.2.4. Multiple Key Press
 * 3.2.4.2.1. Send Mouse Movement
 * 3.2.4.2.3. Left Click
 * 3.2.4.2.4. Right Click
 * 3.2.4.2.5. Scrolling
 * 3.2.5.2.1. Send Mouse Movement
 * 3.2.5.2.4. Left Click
 * 3.2.5.2.5. Right Click
 * 3.2.5.2.6. Scrolling
 * 3.2.5.2.8. Middle Mouse Button
 * 3.2.7.2.1. Press Gamepad Button
 * 3.2.7.2.3. Press Toggle Button
 * 3.2.7.2.7. Multiple Input Press
 * 3.2.9.2.1. Send Sound
 * 3.2.10.2.1. Send Video
 * 3.2.10.2.2. Send Audio
 * Created by Brendan on 3/13/2016.
 */
public class BluetoothTransmitter {

    private BluetoothTransmitter() {}
    private static final String TAG = "BluetoothTransmitter";

    protected static void sendData(byte[] data) throws IOException {
        //TODO Make sure this if statement is never entered. Make sure that sendData is only called when connected.
        if (InitialConnectionTranscever.globalSocket == null) {
            Log.e(TAG, "Shouldn't be calling send data when not connected.");
            return;
        }
        OutputStream outStream = InitialConnectionTranscever.globalSocket.getOutputStream();
        outStream.write(data);
        outStream.flush();
    }

}
