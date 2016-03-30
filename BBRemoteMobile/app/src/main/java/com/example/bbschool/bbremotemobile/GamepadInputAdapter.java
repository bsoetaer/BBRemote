package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Braeden on 3/30/2016.
 */
public class GamepadInputAdapter extends ArrayAdapter<GamepadInput> {
    int layoutResourceId;

    public GamepadInputAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
    }

    public GamepadInputAdapter(Context context, int layoutResourceId, GamepadInput[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GamepadInput gamepadInput = getItem(position);
        GamepadInputHolder holder = null;

        if(convertView == null)
        {
            holder = new GamepadInputHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder.buttonImage = (ImageView)convertView.findViewById(R.id.gamepadInput);

            convertView.setTag(holder);
        }
        else
        {
            holder = (GamepadInputHolder)convertView.getTag();
        }

        holder.buttonImage.setImageResource(GamepadInputImage.getImageId(gamepadInput));

        return convertView;
    }

    static class GamepadInputHolder
    {
        ImageView buttonImage;
    }
}
