package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brendan on 3/13/2016.
 */
public class manualBluetoothTest extends InstrumentationTestCase {

    private BluetoothDevice device = null;

    public void setUp() throws Exception {
        ConnectionFragment scanner = new ConnectionFragment();
        //scanner.getPairedDevices(new TestCallback());
        InitialConnectionTranscever.connect(device);
    }

    /*public void testChangeMode() throws Exception {
        BluetoothModeChangeTransmitter transmitter = new BluetoothModeChangeTransmitter();
        transmitter.changeMode(Mode.Keyboard);
    }*/

    public void testSendMouseData() throws Exception {

        BluetoothModeChangeTransmitter.changeMode(Mode.Optical);

        Map<Integer, Byte> movement = new HashMap<>();
        movement.put(MouseAxis.X.getVal(), Byte.decode("0x64"));
        movement.put(MouseAxis.X.getVal(), Byte.decode("0x10"));

        BluetoothAxisTransmitter.sendMovement(movement);

        //BluetoothAxisTransmitter

        //Map<Integer, Boolean> buttonPresses = new HashMap<>();
        //buttonPresses.put(MouseButton.LEFT.getVal(), Boolean.TRUE);

        //ButtonBluetoothTransmitter.sendKeys(buttonPresses);
    }

    private class TestCallback implements FindDeviceCallback {
        @Override
        public Void call(BluetoothDevice bluetoothDevice) {
            device = bluetoothDevice;
            return null;
        }
    }

}
