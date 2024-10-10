package com.nguyenhungtuan.lab2.cases;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyenhungtuan.lab2.MainActivity;
import com.nguyenhungtuan.lab2.R;

public class Case4Activity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case4);

        // map ui components
        btnBack = findViewById(R.id.btnBack);

        // setup click events
        btnBack.setOnClickListener(v -> navigateToMainScreen());
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}