package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to transmit axis movements for the mouse and gamepad sticks across to Windows.
 * 3.2.4.2.1. Send Mouse Movement
 * 3.2.5.2.1. Send Mouse Movement
 * 3.2.7.2.3. Press Toggle Button
 * Created by Brendan on 3/24/2016.
 */
public class BluetoothAxisTransmitter {

    public static void sendSingleMovement(Integer axisId, Byte val) throws IOException {
        Map<Integer, Byte> movements = new HashMap<>();
        movements.put(axisId, val);
        sendMovement(movements);
    }

    public static void sendMovement(Map<Integer, Byte> axisMovements) throws IOException {
        /* format of encoded bytes is:
        - The first byte is a special code: -1 is change mode, 0 is button press, and 1 is axis
        - for axis movement press, every evenly indexed byte (past the first)
            is the axis it represents, and every odd indexed byte the magnitude of the movement
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
