package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Brendan on 3/24/2016.
 */
public class BluetoothAxisTransmitter {

    public static void sendMovement(Map<Integer, Byte> axisMovements) throws IOException {
        /* format of encoded bytes is:
        - The first byte is a special code: -1 is change mode, 0 is button press, and 1 is axis
        - for axis movement press, every evenly indexed byte (past the first)
            is a the axis it represents, and every odd indexed byte the magnitude of the movement
        */
        byte[] bytes = new byte[1+axisMovements.size()*2];
        bytes[0] = 1;

        int index = 1;
        for (Integer key : axisMovements.keySet()) {
            bytes[index++] = (byte) key.intValue();
            bytes[index++] = axisMovements.get(key);
        }

        BluetoothTransmitter.sendData(bytes);
    }

}
