package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Brendan on 3/13/2016.
 */
public abstract class BluetoothTransmitter {

    private InputStream inStream;
    private OutputStream outStream;

    public BluetoothTransmitter() throws IOException {
        inStream = InitialConnectionTranscever.globalSocket.getInputStream();
        outStream = InitialConnectionTranscever.globalSocket.getOutputStream();
    }

    protected void sendData(byte[] data) throws IOException {
        outStream.write(data);
    }

}
