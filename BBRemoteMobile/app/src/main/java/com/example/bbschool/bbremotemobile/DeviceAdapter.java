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

/**
 * Created by Braeden on 3/23/2016.
 */
public class DeviceAdapter extends ArrayAdapter<BluetoothDevice>{
    Context context;
    int layoutResourceId;
    BluetoothDevice data[] = null;

    public DeviceAdapter(Context context, int layoutResourceId, BluetoothDevice[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DeviceHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DeviceHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.deviceTitle);
            holder.txtAddress = (TextView)row.findViewById(R.id.deviceAddress);

            row.setTag(holder);
        }
        else
        {
            holder = (DeviceHolder)row.getTag();
        }

        BluetoothDevice device = data[position];
        holder.txtTitle.setText(device.getName());
        holder.txtAddress.setText(device.getAddress());

        return row;
    }

    static class DeviceHolder
    {
        TextView txtTitle;
        TextView txtAddress;
    }
}
