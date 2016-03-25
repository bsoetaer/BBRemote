package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Braeden-MSI on 3/24/2016.
 */
public class ConnectionReceiver {

    private static final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                devices.clear();
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device);
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                deviceAdapter.clear();
                deviceAdapter.addAll(devices);
                deviceAdapter.notifyDataSetChanged();
            }
        }
    };

    public static void register(Activity activity) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        activity.registerReceiver(mReceiver, filter);
    }

    public static void unregister(Activity activity) {
        activity.unregisterReceiver(mReceiver);
    }
}
