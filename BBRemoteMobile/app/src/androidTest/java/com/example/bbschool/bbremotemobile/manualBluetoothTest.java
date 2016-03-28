package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.test.InstrumentationTestCase;

import java.io.IOException;
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
    }

    /*public void testChangeMode() throws Exception {
        BluetoothModeChangeTransmitter transmitter = new BluetoothModeChangeTransmitter();
        transmitter.changeMode(Mode.Keyboard);
    }*/

    public void testSendMouseData() throws Exception {

        //BluetoothModeChangeTransmitter.changeMode(Mode.Keyboard);

        //Map<Integer, Byte> movement = new HashMap<>();
        //movement.put(MouseAxis.X.getVal(), Byte.decode("0x64"));
        //movement.put(MouseAxis.Y.getVal(), Byte.decode("0x10"));

        //BluetoothAxisTransmitter.sendMovement(movement);

        Map<Integer, Boolean> buttonPresses = new HashMap<>();
        buttonPresses.put(4, Boolean.TRUE);
        buttonPresses.put(5, Boolean.FALSE);
        //buttonPresses.put(6, Boolean.TRUE);

        ButtonBluetoothTransmitter.sendKeys(buttonPresses);
    }

    private class TestCallback implements FindDeviceCallback {
        @Override
        public Void call(BluetoothDevice bluetoothDevice) {
            device = bluetoothDevice;
            try {
                InitialConnectionTranscever.connect(device);
            } catch (IOException e){
                int a = 1;
            }
            return null;
        }
    }

}
