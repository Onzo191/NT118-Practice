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

public class StudentDetailActivity extends AppCompatActivity {
    private EditText etStudentId, etName, etPhoneNumber;
    private DatabaseHandler dbHandler;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etStudentId = findViewById(R.id.et_student_id);
        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnDelete = findViewById(R.id.btn_delete);

        dbHandler = new DatabaseHandler(this);
        id = getIntent().getIntExtra("STUDENT_ID", -1);

        loadStudentDetails();

        btnUpdate.setOnClickListener(v -> updateStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
    }

    private void loadStudentDetails() {
        Student student = dbHandler.getStudentById(id);
        if (student != null) {
            etStudentId.setText(student.getStudentId());
            etName.setText(student.getName());
            etPhoneNumber.setText(student.getPhoneNumber());
        }
    }

    private void updateStudent() {
        String studentId = etStudentId.getText().toString();
        String name = etName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        if (!name.isEmpty() && !phoneNumber.isEmpty()) {
            dbHandler.updateStudent(new Student(id, studentId, name, phoneNumber));
            finish();
        }
    }

    private void deleteStudent() {
        dbHandler.deleteStudent(id);
        finish();
    }
}