package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Async task for setting up bluetooth connections.
 * 3.2.1.2.1. Connect Device Successfully
 * 3.2.1.2.3. Handle Failed Attempt to Connect to Computer Via Bluetooth
 * Created by Braeden-MSI on 3/24/2016.
 */
public class ConnectAsyncTask extends AsyncTask<BluetoothDevice, Integer, Exception> {

    private ConnectTaskListener listener;

    interface ConnectTaskListener {
        void onConnectStart();
        void onConnectFinished(Exception e);
    }

    public ConnectAsyncTask(ConnectTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onConnectStart();
    }

    @Override
    protected Exception doInBackground(BluetoothDevice... params) {
        try {
            InitialConnectionTranscever.connect(params[0]);
        } catch (IOException e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception result) {
        listener.onConnectFinished(result);
    }
}
