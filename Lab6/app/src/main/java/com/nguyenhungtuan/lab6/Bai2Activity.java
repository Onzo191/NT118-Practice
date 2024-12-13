package com.nguyenhungtuan.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

public class Bai2Activity extends AppCompatActivity {

    private PowerStateChangeReceiver powerStateChangeReceiver;
    Button btnBack;

    private void initValue() {
        powerStateChangeReceiver = new PowerStateChangeReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);


        initValue();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v->navigateTo(MainActivity.class));

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(
                powerStateChangeReceiver,
                intentFilter
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerStateChangeReceiver);
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}