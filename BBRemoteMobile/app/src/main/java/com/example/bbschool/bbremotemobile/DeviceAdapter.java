package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * ArrayAdapter for populating paired and unpaired device lists of the ConnectionFragment.
 * 3.2.1.2.4. Scan for Devices
 * Created by Braeden on 3/23/2016.
 */
public class DeviceAdapter extends ArrayAdapter<BluetoothDevice>{
    int layoutResourceId;

    public DeviceAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
    }

    public DeviceAdapter(Context context, int layoutResourceId, ArrayList<BluetoothDevice> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice device = getItem(position);
        DeviceHolder holder = null;

        if(convertView == null)
        {
            holder = new DeviceHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.deviceTitle);
            holder.txtAddress = (TextView)convertView.findViewById(R.id.deviceAddress);

            convertView.setTag(holder);
        }
        else
        {
            holder = (DeviceHolder)convertView.getTag();
        }

        holder.txtTitle.setText(device.getName());
        holder.txtAddress.setText(device.getAddress());

        return convertView;
    }

    static class DeviceHolder
    {
        TextView txtTitle;
        TextView txtAddress;
    }
}
