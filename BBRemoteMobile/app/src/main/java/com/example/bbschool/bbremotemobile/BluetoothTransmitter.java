package com.example.bbschool.bbremotemobile;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
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
    }

}
