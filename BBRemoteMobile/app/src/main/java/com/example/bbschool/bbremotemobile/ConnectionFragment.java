package com.example.bbschool.bbremotemobile;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brendan on 3/13/2016.
 */
public class ConnectionFragment extends Fragment implements ListView.OnItemClickListener, ConnectAsyncTask.ConnectTaskListener {

    private Set<BluetoothDevice> devices;
    private DeviceAdapter deviceAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private boolean isConnecting = false;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                devices.clear();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                deviceAdapter.clear();
                deviceAdapter.addAll(devices);
                deviceAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        deviceAdapter = new DeviceAdapter(getContext(), R.layout.device_item);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView deviceList = (ListView) getView().findViewById(R.id.deviceList);
        deviceList.setAdapter(deviceAdapter);
        deviceList.setOnItemClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver));
    }

    public void onConnectStart() {
        isConnecting = true;
    }

    public void onConnectFinished(Exception e) {
        isConnecting = false;
        if (e == null) {
            ModeChanger modeChanger = new ModeChanger(getActivity());
            modeChanger.changeInputMode(Mode.Keyboard);
            return;
        }
        //TODO uncomment this after the custom exception class is added.
        // else if (e.getClass() == CustomException.class) {
           // connectionFailedDialog("Desktop client is not being run on the selected device");
        //}
        else if (e.getClass() == IOException.class) {
            connectionFailedDialog("Class: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    public void onItemClick(AdapterView adapter, View v, int position, long id) {
        // Do something when a list item is clicked
        BluetoothDevice device = (BluetoothDevice) adapter.getItemAtPosition(position);
        bluetoothAdapter.cancelDiscovery();
        ConnectAsyncTask task = new ConnectAsyncTask(this);
        task.execute(device);
    }

    private void connectionFailedDialog(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg);
        builder.setTitle("Connection Failed");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}