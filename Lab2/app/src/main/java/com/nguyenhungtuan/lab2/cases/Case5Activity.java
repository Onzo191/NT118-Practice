package com.nguyenhungtuan.lab2.cases;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyenhungtuan.lab2.MainActivity;
import com.nguyenhungtuan.lab2.Model.Employee;
import com.nguyenhungtuan.lab2.R;
import com.nguyenhungtuan.lab2.adapter.EmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class Case5Activity extends AppCompatActivity {

    private Button btnBack, btnAdd;
    private EditText edtEmployeeId, edtEmployeeFullName;
    private CheckBox cbIsManager;
    private ListView lvEmployees;

    private List<Employee> employeeList;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case5);
        initUI();
        setupListeners();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack);
        edtEmployeeId = findViewById(R.id.edtName);
        edtEmployeeFullName = findViewById(R.id.spThumbnail);
        cbIsManager = findViewById(R.id.cbIsPromotion);
        btnAdd = findViewById(R.id.btnAdd);
        lvEmployees = findViewById(R.id.lvEmployees);

        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(this, R.layout.item__employee, employeeList);
        lvEmployees.setAdapter(employeeAdapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> navigateToMainScreen());
        btnAdd.setOnClickListener(v -> addEmployee());
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void addEmployee() {
        String id = edtEmployeeId.getText().toString().trim();
        String fullName = edtEmployeeFullName.getText().toString().trim();
        boolean isManager = cbIsManager.isChecked();

        if (validateInput(id, fullName)) {
            Employee newEmployee = new Employee(id, fullName, isManager);
            employeeList.add(newEmployee);
            employeeAdapter.notifyDataSetChanged();
            clearInputFields();
        }
    }

    private boolean validateInput(String id, String fullName) {
        if (TextUtils.isEmpty(id)) {
            showToast("ID is required");
            return false;
        }

        if (employeeAdapter.doesIdExist(id)) {
            showToast("ID already exists");
            return false;
        }

        if (TextUtils.isEmpty(fullName)) {
            showToast("Full Name is required");
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        edtEmployeeId.setText("");
        edtEmployeeFullName.setText("");
        cbIsManager.setChecked(false);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
