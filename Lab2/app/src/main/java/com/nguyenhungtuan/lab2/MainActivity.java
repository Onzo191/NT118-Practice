package com.nguyenhungtuan.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.nguyenhungtuan.lab2.cases.Case1Activity;
import com.nguyenhungtuan.lab2.cases.Case3Activity;
import com.nguyenhungtuan.lab2.cases.Case4Activity;
import com.nguyenhungtuan.lab2.cases.Case5Activity;
import com.nguyenhungtuan.lab2.cases.DishActivity;
import com.nguyenhungtuan.lab2.cases.RecyclerActivity;

public class MainActivity extends AppCompatActivity {
    private String[] caseNames = {
            "Trường hợp 1",
            "Trường hợp 3",
            "Trường hợp 4",
            "Trường hợp 5",
            "Grid & Spinner",
            "Recycler View"
    };
    private Class<?>[] caseActivities = {
            Case1Activity.class,
            Case3Activity.class,
            Case4Activity.class,
            Case5Activity.class,
            DishActivity.class,
            RecyclerActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout layout = createConstraintLayout();
        addButtonsToLayout(layout);

        setContentView(layout);
    }

    private ConstraintLayout createConstraintLayout() {
        ConstraintLayout layout = new ConstraintLayout(this);
        layout.setId(R.id.main);

        layout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        // app padding
        float density = getResources().getDisplayMetrics().density;
        int paddingVertical = (int) (density * 16);
        int paddingHorizontal = (int) (density * 16);
        layout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);

        return layout;
    }

    private void addButtonsToLayout(ConstraintLayout layout) {
        int marginTop = 12;
        int previousButtonId = 0;

        for (int i = 0; i < caseNames.length; i++) {
            Button button = createButton(caseNames[i]);
            layout.addView(button);

            applyConstraintsToButton(layout, button, previousButtonId, marginTop);

            setButtonClickListener(button, i);

            previousButtonId = button.getId();
        }
    }

    private Button createButton(String text) {
        Button button = new Button(this);
        button.setId(View.generateViewId());
        button.setText(text);
        button.setLayoutParams(new ConstraintLayout.LayoutParams(
                //ConstraintLayout.LayoutParams.WRAP_CONTENT,
                0,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        return button;
    }

    private void applyConstraintsToButton(ConstraintLayout layout, Button button, int previousButtonId, int marginTop) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        constraintSet.connect(button.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        if (previousButtonId == 0) {
            constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        } else {
            constraintSet.connect(button.getId(), ConstraintSet.TOP, previousButtonId, ConstraintSet.BOTTOM, marginTop);
        }

        constraintSet.applyTo(layout);
    }

    private void setButtonClickListener(Button button, final int index) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, caseActivities[index]);
                startActivity(intent);
            }
        });
    }
}