package com.nguyenhungtuan.lab3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenhungtuan.lab3.MainActivity;
import com.nguyenhungtuan.lab3.R;
import com.nguyenhungtuan.lab3.db.DatabaseHandlerEx2;
import com.nguyenhungtuan.lab3.model.Contact;

import java.util.List;

public class exercise2Activity extends AppCompatActivity {

    private DatabaseHandlerEx2 dbHandler;
    private ListView lvContacts;
    private EditText etName, etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> navigateToMainScreen());

        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        lvContacts = findViewById(R.id.lv_contacts);
        Button btnAddContact = findViewById(R.id.btn_add_contact);

        dbHandler = new DatabaseHandlerEx2(this);
        addDefaultContacts();
        displayContacts();

        btnAddContact.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();

            if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                dbHandler.addContact(new Contact(0, name, phoneNumber));
                etName.setText("");
                etPhoneNumber.setText("");
                displayContacts();
            }
        });
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void addDefaultContacts() {
        if (dbHandler.getAllContacts().isEmpty()) {
            String[][] defaultContacts = {
                    {"Ravi", "9100000000"},
                    {"Srinivas", "9199999999"},
                    {"Tommy", "9522222222"},
                    {"Karthik", "9533333333"}
            };

            for (String[] contactData : defaultContacts) {
                dbHandler.addContact(new Contact(0, contactData[0], contactData[1]));
            }
        }
    }

    private void displayContacts() {
        List<Contact> contacts = dbHandler.getAllContacts();

        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, R.layout.item_contact, contacts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_contact, parent, false);
                }

                TextView tvName = convertView.findViewById(R.id.tv_name);
                TextView tvPhoneNumber = convertView.findViewById(R.id.tv_phone_number);
                Button btnDelete = convertView.findViewById(R.id.btn_delete);

                Contact contact = getItem(position);
                if (contact != null) {
                    tvName.setText(contact.getName());
                    tvPhoneNumber.setText(contact.getPhoneNumber());
                }

                btnDelete.setOnClickListener(v -> {
                    dbHandler.deleteContact(contact.getId());
                    displayContacts();
                });

                return convertView;
            }
        };

        lvContacts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayContacts();
    }
}