package com.nguyenhungtuan.lab6;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SmsHandler {

    private static final String TAG = "SmsHandler";

    private ReentrantLock lock;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> requesters;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Context context;

    public SmsHandler(Context context) {
        this.context = context;
        initVariables();
    }

    private void initVariables() {
        lock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(context, R.layout.simple_list_item_1, requesters);

        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setupBroadcastReceiver() {
        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        if (pdus != null) {
                            ArrayList<String> addresses = new ArrayList<>();
                            for (Object pdu : pdus) {
                                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                                String sender = smsMessage.getDisplayOriginatingAddress();
                                addresses.add(sender);
                            }
                            processAddresses(addresses);
                        }
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(smsReceiver, filter);
    }

    private void processAddresses(ArrayList<String> addresses) {
        lock.lock();
        try {
            for (String address : addresses) {
                if (!requesters.contains(address)) {
                    requesters.add(address);
                }
            }
            adapter.notifyDataSetChanged();
        } finally {
            lock.unlock();
        }

        boolean autoResponse = sharedPreferences.getBoolean("auto_response", false);
        if (autoResponse) {
            respondToAll(true);
        }
    }

    public void respondToAll(boolean isSafe) {
        String message = isSafe
                ? context.getString(R.string.i_am_safe_and_well_worry_not)
                : context.getString(R.string.tell_my_mother_i_love_her);

        ArrayList<String> recipients;
        lock.lock();
        try {
            recipients = new ArrayList<>(requesters);
            requesters.clear();
            adapter.notifyDataSetChanged();
        } finally {
            lock.unlock();
        }

        SmsManager smsManager = SmsManager.getDefault();
        for (String recipient : recipients) {
            smsManager.sendTextMessage(recipient, null, message, null, null);
            Toast.makeText(context, "Message sent to: " + recipient, Toast.LENGTH_SHORT).show();
        }
    }
}
