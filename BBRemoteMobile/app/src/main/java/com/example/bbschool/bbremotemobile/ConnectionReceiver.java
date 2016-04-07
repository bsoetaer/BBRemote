package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;

/**
 * Created by Braeden-MSI on 3/24/2016.
 */
public class ConnectionReceiver extends BroadcastReceiver implements ConnectAsyncTask.ConnectTaskListener {

    private static final long RECONNECT_TIME = 60;
    private long startTime;
    private boolean tryReconnect = false;
    private Context context;
    private AlertDialog dialog;
    private BluetoothDevice device;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();

        // When discovery finds a device
        if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            // Safety net in case this gets called while we are already trying to reconnect
            if ( tryReconnect || !ConnectionState.isConnected() )
                return;
            tryReconnect = true;
            ConnectionState.setConnected(false);
            startTime = System.currentTimeMillis()/1000;
            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            displayRetryDialog();
            retryConnection(device);
        }
    }

    public void onConnectStart() {
    }

    public void onConnectFinished(Exception e) {
        if( !tryReconnect )
            return;

        if (e != null) {
            if( ((System.currentTimeMillis()/1000) - startTime) > RECONNECT_TIME)
                cancelRetry();
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {}
                retryConnection(device);
            }
        } else {
            ConnectionState.setConnected(true);
            dialog.dismiss();
            tryReconnect = false;
        }
    }

    private void retryConnection(BluetoothDevice device) {
        if ( !tryReconnect )
            return;
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        ConnectAsyncTask task = new ConnectAsyncTask(this);
        task.execute(device);
    }

    private void displayRetryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Trying to resume connection");
        builder.setTitle("Connection Lost");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancelRetry();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void cancelRetry() {
        dialog.dismiss();
        ConnectionState.setConnected(false);
        ModeChanger.disconnected(context);
        tryReconnect = false;
    }

    private static final BroadcastReceiver mReceiver = new ConnectionReceiver();

    public static void register(Activity activity) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        activity.registerReceiver(mReceiver, filter);
    }

    public static void unregister(Activity activity) {
        activity.unregisterReceiver(mReceiver);
    }
}
