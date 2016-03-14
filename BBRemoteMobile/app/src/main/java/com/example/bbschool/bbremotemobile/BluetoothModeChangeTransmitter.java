package com.example.bbschool.bbremotemobile;

import java.io.IOException;

/**
 * Created by Brendan on 3/13/2016.
 */
public class BluetoothModeChangeTransmitter extends BluetoothTransmitter {

    public BluetoothModeChangeTransmitter() throws IOException {
        super();
    }

    public void changeMode(Mode mode) throws IOException {
        byte[] data = new byte[2];
        data[0] = (byte) 255; // 0b11111111 is the special code for switching modes
        data[1] = (byte) mode.getVal();
        sendData(data);
    }

}
