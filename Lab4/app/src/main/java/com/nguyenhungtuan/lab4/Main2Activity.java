package com.nguyenhungtuan.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnBack = findViewById(R.id.button_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
            finish();
        });
    }
}