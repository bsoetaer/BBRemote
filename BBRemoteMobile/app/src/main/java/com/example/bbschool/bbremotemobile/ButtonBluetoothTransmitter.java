package com.example.bbschool.bbremotemobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to transmit button presses and releases on the Android device to the Windows system
 * 3.2.3.2.1. Send Key Input
 * 3.2.3.2.4. Multiple Key Press
 * 3.2.4.2.3. Left Click
 * 3.2.4.2.4. Right Click
 * 3.2.5.2.4. Left Click
 * 3.2.5.2.5. Right Click
 * 3.2.5.2.8. Middle Mouse Button
 * 3.2.7.2.1. Press Gamepad Button
 * 3.2.7.2.7. Multiple Input Press
 * Created by Brendan on 3/13/2016.
 */
public final class ButtonBluetoothTransmitter {

    public static void sendSingleKeyUp(Integer buttonValue) throws IOException {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(buttonValue, Boolean.FALSE);
        sendKeys(map);
    }

    public static void sendSingleKeyDown(Integer buttonValue) throws IOException {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(buttonValue, Boolean.TRUE);
        sendKeys(map);
    }

    public static void sendKeys(Map<Integer, Boolean> keyPresses) throws IOException {
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
        return (byte) (bool ? -1 : 0);
    }
}
