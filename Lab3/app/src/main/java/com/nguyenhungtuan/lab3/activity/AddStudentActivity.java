package com.nguyenhungtuan.lab3.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenhungtuan.lab3.R;
import com.nguyenhungtuan.lab3.db.DatabaseHandler;
import com.nguyenhungtuan.lab3.model.Student;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etStudentId, etName, etPhoneNumber;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etStudentId = findViewById(R.id.et_student_id);
        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        dbHandler = new DatabaseHandler(this);

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> saveStudent());
    }

    private void saveStudent() {
        String studentId = etStudentId.getText().toString();
        String name = etName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        if (!name.isEmpty() && !phoneNumber.isEmpty()) {
            dbHandler.addStudent(new Student(0, studentId, name, phoneNumber));
            finish();
        }
    }
}