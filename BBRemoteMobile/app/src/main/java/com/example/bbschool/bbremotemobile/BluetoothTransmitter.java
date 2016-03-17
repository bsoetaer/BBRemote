package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Brendan on 3/13/2016.
 */
public abstract class BluetoothTransmitter {

    private BluetoothTransmitter() {}

    protected static void sendData(byte[] data) throws IOException {
        OutputStream outStream = InitialConnectionTranscever.globalSocket.getOutputStream();
        outStream.write(data);
    }

}
