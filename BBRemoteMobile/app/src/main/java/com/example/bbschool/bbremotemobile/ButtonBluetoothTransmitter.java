package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Brendan on 3/13/2016.
 */
public class ButtonBluetoothTransmitter extends BluetoothTransmitter {

    public ButtonBluetoothTransmitter() throws IOException {
        super();
    }

    public void sendKeys(Map<Integer, Boolean> keyPresses) throws IOException {
        /* format of encoded bytes is: every evenly indexed byte is a key code, and every odd
         inidexed byte is either 0 (key up) or 1 (key down)*/
        byte[] bytes = new byte[keyPresses.size()*2];

        int index = 0;
        for (Integer key : keyPresses.keySet()) {
            bytes[index++] = (byte) key.intValue();
            bytes[index++] = booleanToByte(keyPresses.get(key));
        }

        sendData(bytes);
    }

    private byte booleanToByte(Boolean bool) {
        return (byte) (bool ? 1 : 0);
    }
}