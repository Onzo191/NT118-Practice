package com.nguyenhungtuan.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Bai1Activity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private TextView tvContent;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        tvContent = findViewById(R.id.tv_content);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> navigateTo(MainActivity.class));

        initBroadcastReceiver();
    }

    private void initBroadcastReceiver() {
        intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        broadcastReceiver = new MessageReceiver(tvContent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
