package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

/**
 * Created by Brendan on 3/13/2016.
 */
public class DeviceScanner {
    private BluetoothAdapter bluetoothAdapter;

    public DeviceScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void getPairedDevices(FindDeviceCallback callback) {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices)
            device.
            callback.call(device);
    }

    public void getUnpairedDevices(FindDeviceCallback callback) {

    }

    public void cancelDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }
}