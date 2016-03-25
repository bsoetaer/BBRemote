package com.example.bbschool.bbremotemobile;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Brendan on 3/13/2016.
 */
public final class ButtonBluetoothTransmitter{

    private ButtonBluetoothTransmitter() {}

    public static void sendKeys(Map<Integer, Boolean> keyPresses) throws IOException{
        /* format of encoded bytes is:
        - The first byte is a special code: -1 is change mode, 0 is button press, and 1 is axis
        - for button press, every evenly indexed byte (past the first)
            is a key code, and every odd indexed byte is either 0 (key up) or -1 (key down)
        */
        byte[] bytes = new byte[1+keyPresses.size()*2];
        bytes[0] = 0;

        int index = 1;
        for (Integer key : keyPresses.keySet()) {
            bytes[index++] = (byte) key.intValue();
            bytes[index++] = booleanToByte(keyPresses.get(key));
        }

        BluetoothTransmitter.sendData(bytes);
    }

    private static byte booleanToByte(Boolean bool) {
        return (byte) (bool ? 1 : -1);
    }
}
