package com.nguyenhungtuan.lab3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenhungtuan.lab3.MainActivity;
import com.nguyenhungtuan.lab3.R;
import com.nguyenhungtuan.lab3.adapter.StudentAdapter;
import com.nguyenhungtuan.lab3.db.DatabaseHandler;
import com.nguyenhungtuan.lab3.model.Contact;
import com.nguyenhungtuan.lab3.model.Student;

import java.util.List;

public class StudentActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private DatabaseHandler dbHandler;
    private RecyclerView rvStudents;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> navigateToMainScreen());

        dbHandler = new DatabaseHandler(this);
        addDefaultStudents();
        rvStudents = findViewById(R.id.rv_students);
        Button btnAddStudent = findViewById(R.id.btn_add_student);
        btnAddStudent.setOnClickListener(v -> openAddStudentActivity());

        displayStudents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayStudents();
    }

    private void addDefaultStudents() {
        if (dbHandler.getAllStudents().isEmpty()) {
            String[][] defaultStudents = {
                    {"21521999", "Ravi", "9100000000"},
                    {"21521998", "Srinivas", "9199999999"},
                    {"21521997", "Tommy", "9522222222"},
                    {"21521996", "Karthik", "9533333333"}
            };

            for (String[] studentData : defaultStudents) {
                dbHandler.addStudent(new Student(0, studentData[0], studentData[1], studentData[2]));
            }
        }
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayStudents() {
        List<Student> students = dbHandler.getAllStudents();
        studentAdapter = new StudentAdapter(students, this);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);
    }

    private void openAddStudentActivity() {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStudentClick(Student student) {
        Intent intent = new Intent(this, StudentDetailActivity.class);
        intent.putExtra("STUDENT_ID", student.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int studentId) {
        dbHandler.deleteStudent(studentId);
        displayStudents();
    }
}