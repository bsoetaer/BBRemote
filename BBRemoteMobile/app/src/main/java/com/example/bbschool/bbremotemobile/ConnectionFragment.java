package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Fragment to allow users to connect to windows devices via bluetooth.
 * 3.2.1. Connect to Computer Via Bluetooth
 * and sub requirements
 * 3.2.1.2.1. Connect Device Successfully
 * 3.2.1.2.2. Only Allow Connection Between Paired Devices
 * 3.2.1.2.3. Handle Failed Attempt to Connect to Computer Via Bluetooth
 * 3.2.1.2.4. Scan for Devices
 * 3.2.1.2.8. Auto-connect to Last Device on Startup
 * Created by Brendan on 3/13/2016.
 */
public class ConnectionFragment extends Fragment implements ConnectAsyncTask.ConnectTaskListener {

    private ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>();
    private ArrayList<BluetoothDevice> unpairedDevices = new ArrayList<>();
    private DeviceAdapter pairedDevicesAdapter;
    private DeviceAdapter unpairedDevicesAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private boolean isConnecting = false;
    private boolean isScanning = false;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new ScannerReceiver();

    private class ScannerReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                clearLists();
                Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                for (BluetoothDevice device : devices)
                    pairedDevices.add(device);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                addDevice(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addDevice(device);
            }
        }

        private void clearLists() {
            pairedDevices.clear();
            unpairedDevices.clear();
            pairedDevicesAdapter.notifyDataSetChanged();
            unpairedDevicesAdapter.notifyDataSetChanged();
        }

        private void addDevice(BluetoothDevice device) {
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                if (!pairedDevices.contains(device)) {
                    pairedDevices.add(device);
                }
                unpairedDevices.remove(device);
            } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                if (!unpairedDevices.contains(device)) {
                    unpairedDevices.add(device);
                }
                pairedDevices.remove(device);
            }
            pairedDevicesAdapter.notifyDataSetChanged();
            unpairedDevicesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        pairedDevicesAdapter = new DeviceAdapter(getContext(), R.layout.device_item, pairedDevices);
        unpairedDevicesAdapter = new DeviceAdapter(getContext(), R.layout.device_item, unpairedDevices);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        getActivity().registerReceiver(mReceiver, filter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.connection_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.getItemId() == R.id.action_scan ){
            if ( !bluetoothAdapter.isDiscovering() ) {
                bluetoothAdapter.startDiscovery();
                Toast toast = Toast.makeText(getContext(), "Scanning for devices", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getContext(), "Already scanning", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        ListView pairedDeviceList = (ListView) getView().findViewById(R.id.pairedDeviceList);
        pairedDeviceList.setAdapter(pairedDevicesAdapter);
        pairedDeviceList.setOnItemClickListener(new pairedDeviceClickListener());
        ListView unpairedDeviceList = (ListView) getView().findViewById(R.id.unpairedDeviceList);
        unpairedDeviceList.setAdapter(unpairedDevicesAdapter);
        unpairedDeviceList.setOnItemClickListener(new unpairedDeviceClickListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    public void onConnectStart() {
        isConnecting = true;
    }

    public void onConnectFinished(Exception e) {
        isConnecting = false;
        if (e == null) {
            ConnectionState.setConnected(true);
            ModeChanger modeChanger = new ModeChanger(getActivity());
            modeChanger.changeInputMode(Mode.Keyboard);
        }
        //TODO uncomment this after the custom exception class is added.
        // else if (e.getClass() == CustomException.class) {
           // connectionFailedDialog("Desktop client is not being run on the selected device");
            //ConnectionState.setConnected(false);
        //}
        else if (e.getClass() == IOException.class) {
            connectionFailedDialog("Class: " + e.getClass() + " Message: " + e.getMessage());
            ConnectionState.setConnected(false);
        }
    }

    private class pairedDeviceClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView adapter, View v, int position, long id) {
            // Do something when a list item is clicked
            BluetoothDevice device = (BluetoothDevice) adapter.getItemAtPosition(position);
            bluetoothAdapter.cancelDiscovery();
            ConnectAsyncTask task = new ConnectAsyncTask(ConnectionFragment.this);
            task.execute(device);
            Toast toast = Toast.makeText(getContext(), "Connecting to " + device.getName(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class unpairedDeviceClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView adapter, View v, int position, long id) {
            // Do something when a list item is clicked
            BluetoothDevice device = (BluetoothDevice) adapter.getItemAtPosition(position);
            device.createBond();
            Toast toast = Toast.makeText(getContext(), "Pairing to " + device.getName(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void connectionFailedDialog(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg);
        builder.setTitle("Connection Failed");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}