package com.nguyenhungtuan.lab3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenhungtuan.lab3.MainActivity;
import com.nguyenhungtuan.lab3.R;
import com.nguyenhungtuan.lab3.adapter.exercise1Adapter;

import java.util.ArrayList;
import java.util.List;

public class exercise1Activity extends AppCompatActivity {

    private exercise1Adapter dbAdapter;
    private List<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> navigateToMainScreen());

        dbAdapter = new exercise1Adapter(this);
        dbAdapter.open();

        dbAdapter.deleteAllUsers();
        for (int i = 0; i < 10; i++) {
            dbAdapter.createUser("Nguyễn Văn An " + i);
        }

        users = getData();
        showData();
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private List<String> getData() {
        List<String> users = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = dbAdapter.getAllUsers();
            int nameIndex = cursor.getColumnIndexOrThrow(exercise1Adapter.KEY_NAME);

            while (cursor.moveToNext()) {
                users.add(cursor.getString(nameIndex));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return users;
    }

    private void showData() {
        ListView lvUser = findViewById(R.id.lv_user);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, R.layout.item_user, users);
        lvUser.setAdapter(userAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbAdapter != null) {
            dbAdapter.close();
        }
    }
}