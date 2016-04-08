package com.example.bbschool.bbremotemobile;

import java.io.IOException;

/**
 * Class to transmit the fact that we have changed input modes and are now using a different kind
 * of input device.
 * 3.2.2.2.1. Mode Select with Valid Connection
 * 3.2.6.2.1. Swap to Touchpad Mouse from Keyboard
 * 3.2.6.2.2. Swap to Optical Mouse from Keyboard
 * 3.2.6.2.3. Swap to Keyboard from Touchpad Mouse
 * 3.2.6.2.4. Swap to Keyboard from Optical Mouse
 * Created by Brendan on 3/13/2016.
 */
public class BluetoothModeChangeTransmitter {

    private BluetoothModeChangeTransmitter() {}

    public static void changeMode(Mode mode) throws IOException {
        byte[] data = new byte[2];
        data[0] = (byte) -1; // 0b11111111 is the special code for switching modes
        data[1] = (byte) mode.getVal();
        BluetoothTransmitter.sendData(data);
    }

}
