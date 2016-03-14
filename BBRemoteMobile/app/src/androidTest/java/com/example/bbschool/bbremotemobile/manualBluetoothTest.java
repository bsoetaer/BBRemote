package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.test.InstrumentationTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brendan on 3/13/2016.
 */
public class manualBluetoothTest extends InstrumentationTestCase {

    private BluetoothDevice device = null;

    public void setUp() throws Exception {
        DeviceScanner scanner = new DeviceScanner();
        scanner.getPairedDevices(new TestCallback());
        InitialConnectionTranscever.connect(device);
    }

    /*public void testChangeMode() throws Exception {
        BluetoothModeChangeTransmitter transmitter = new BluetoothModeChangeTransmitter();
        transmitter.changeMode(Mode.Keyboard);
    }*/

    public void testSendKeyboardData() throws Exception {
        ButtonBluetoothTransmitter transmitter = new ButtonBluetoothTransmitter();

        Map<Integer, Boolean> keyPresses = new HashMap<>();
        keyPresses.put(1,Boolean.FALSE);
        keyPresses.put(2,Boolean.TRUE);

        transmitter.sendKeys(keyPresses);
    }

    private class TestCallback implements FindDeviceCallback {
        @Override
        public Void call(BluetoothDevice bluetoothDevice) {
            device = bluetoothDevice;
            return null;
        }
    }

}
