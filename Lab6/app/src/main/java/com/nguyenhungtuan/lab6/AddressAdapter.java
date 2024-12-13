package com.nguyenhungtuan.lab6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> addresses;

    public AddressAdapter(Context context, ArrayList<String> addresses) {
        super(context, R.layout.simple_list_item_1, addresses);
        this.context = context;
        this.addresses = addresses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false);
        }

        String address = addresses.get(position);
        TextView tvAddress = listItem.findViewById(R.id.tv_address);
        tvAddress.setText(address);

        return listItem;
    }
}
