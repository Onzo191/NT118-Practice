package com.nguyenhungtuan.lab2.cases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyenhungtuan.lab2.MainActivity;
import com.nguyenhungtuan.lab2.Model.Dish;
import com.nguyenhungtuan.lab2.Model.Thumbnail;
import com.nguyenhungtuan.lab2.R;
import com.nguyenhungtuan.lab2.adapter.DishAdapter;
import com.nguyenhungtuan.lab2.adapter.ThumbnailAdapter;

import java.util.ArrayList;
import java.util.List;

public class DishActivity extends AppCompatActivity {

    private Button btnBack, btnAdd;
    private EditText edtName;
    private Spinner spThumbnail;
    private CheckBox cbIsPromotion;
    private GridView gvDish;
    private List<Dish> dishList;
    private DishAdapter dishAdapter;
    private ThumbnailAdapter thumbnailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        initUi();
        setupListeners();
    }

    private void initUi() {
        // Map UI components
        btnBack = findViewById(R.id.btnBack);
        edtName = findViewById(R.id.edtName);
        spThumbnail = findViewById(R.id.spThumbnail);
        cbIsPromotion = findViewById(R.id.cbIsPromotion);
        gvDish = findViewById(R.id.gvDish);
        btnAdd = findViewById(R.id.btnAdd);

        // Initialize dish list and adapter
        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList);
        gvDish.setAdapter(dishAdapter);

        // Initialize thumbnail adapter
        thumbnailAdapter = new ThumbnailAdapter(this, getThumbnails());
        spThumbnail.setAdapter(thumbnailAdapter);
        setupThumbnailSpinner();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> navigateToMainScreen());
        btnAdd.setOnClickListener(v -> addDish());
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private List<Thumbnail> getThumbnails() {
        List<Thumbnail> thumbnails = new ArrayList<>();
        thumbnails.add(Thumbnail.L1);
        thumbnails.add(Thumbnail.L2);
        thumbnails.add(Thumbnail.L3);
        thumbnails.add(Thumbnail.L4);
        return thumbnails;
    }

    private void setupThumbnailSpinner() {
        spThumbnail.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                thumbnailAdapter.setSelectedPosition(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void addDish() {
        String name = edtName.getText().toString().trim();
        Thumbnail selectedThumbnail = (Thumbnail) spThumbnail.getSelectedItem();
        boolean isPromotion = cbIsPromotion.isChecked();

        // Validate input
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name for the dish", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new Dish object
        Dish newDish = new Dish(name, selectedThumbnail.getImg(), isPromotion);

        // Add the new dish to the list
        dishList.add(newDish);

        // Notify the adapter that the data has changed
        dishAdapter.notifyDataSetChanged();

        // Reset input fields
        resetInputFields();
    }

    private void resetInputFields() {
        edtName.setText("");
        spThumbnail.setSelection(0);
        thumbnailAdapter.setSelectedPosition(-1);
        cbIsPromotion.setChecked(false);
    }
}
