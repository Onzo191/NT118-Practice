package com.nguyenhungtuan.lab6;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Bai4Activity extends AppCompatActivity {

    private static final String TAG = "Bai4Activity";
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private ListView lvMessages;
    private Button btnSafe, btnMayday, btnBack;

    private ArrayAdapter<String> adapter;
    private List<String> requesters;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final String AUTO_RESPONSE = "auto_response";

    private BroadcastReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);

        // Check and request necessary permissions
        checkPermissions();

        // Initialize views, variables, and listeners
        initViews();
        initVariables();
        handleOnClickListeners();

        // Set up BroadcastReceiver for SMS
        setupBroadcastReceiver();
    }

    private void checkPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), SMS_PERMISSION_REQUEST_CODE);
        }
    }

    private void initViews() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
        btnBack = findViewById(R.id.btnBack);
    }

    private void initVariables() {
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        llButtons.setVisibility(autoResponse ? View.GONE : View.VISIBLE);
    }

    private void handleOnClickListeners() {
        btnSafe.setOnClickListener(view -> respondToOldest(true));
        btnMayday.setOnClickListener(view -> respondToOldest(false));
        btnBack.setOnClickListener(view -> finish());

        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> onAutoResponseChanged(isChecked));
    }

    private void setupBroadcastReceiver() {
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        if (pdus != null) {
                            for (Object pdu : pdus) {
                                //SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu, bundle.getString("format"));
                                handleReceivedSMS(smsMessage);
                            }
                        }
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);
    }

    private void handleReceivedSMS(SmsMessage smsMessage) {
        String sender = smsMessage.getDisplayOriginatingAddress();
        String messageBody = smsMessage.getMessageBody();

        Log.d(TAG, "Received SMS: " + messageBody + " from " + sender);

        if (isRequesterMessage(messageBody)) {
            addRequester(sender);
        }
    }

    private boolean isRequesterMessage(String messageBody) {
        return messageBody.toLowerCase().contains(getString(R.string.are_you_ok).toLowerCase());
    }

    private void addRequester(String sender) {
        if (!requesters.contains(sender)) {
            requesters.add(sender);
            adapter.notifyDataSetChanged();

            if (swAutoResponse.isChecked()) {
                respondToAll(true);
            }
        }
    }

    private void respondToOldest(boolean isSafe) {
        if (requesters.isEmpty()) {
            Toast.makeText(this, "No requesters to respond to.", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = isSafe
                ? getString(R.string.i_am_safe_and_well_worry_not)
                : getString(R.string.tell_my_mother_i_love_her);

        // Get the oldest requester (the first in the list)
        String recipient = requesters.get(0);

        // Send SMS to the first requester
        sendSMSToRecipient(recipient, message);

        // Optionally, remove the first requester from the list after responding
        requesters.remove(0);
        adapter.notifyDataSetChanged();
    }

    private void respondToAll(boolean isSafe) {
        String message = isSafe
                ? getString(R.string.i_am_safe_and_well_worry_not)
                : getString(R.string.tell_my_mother_i_love_her);

        // Make a copy of the requesters list before clearing it to prevent modification during iteration
        List<String> recipients = new ArrayList<>(requesters);
        requesters.clear();
        adapter.notifyDataSetChanged();

        sendSMSToAll(recipients, message);
    }

    private void sendSMSToAll(List<String> recipients, String message) {
        for (String recipient : recipients) {
            sendSMSToRecipient(recipient, message);
        }
    }

    private void sendSMSToRecipient(String recipient, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(recipient, null, message, null, null);
        Toast.makeText(this, "Sent to: " + recipient + ", Message: " + message, Toast.LENGTH_SHORT).show();
    }

    private void onAutoResponseChanged(boolean isChecked) {
        llButtons.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        editor.putBoolean(AUTO_RESPONSE, isChecked);
        editor.apply();

        if (isChecked && !requesters.isEmpty()) {
            respondToAll(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
