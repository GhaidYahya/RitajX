package com.example.ritajx;


import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.widget.AppCompatImageView;

public class Library extends AppCompatActivity {

    private TextView textView;
    private Button button;

    private AppCompatImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        textView = findViewById(R.id.show);
        button = findViewById(R.id.click);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(Library.this, Service.class);
                startActivity(intent);

                // Optionally, if you want to finish the current Activity
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker();

                openTimePicker();
            }
        });


    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                textView.setText(String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day));

            }
        }, 2024, 01, 20);

        datePickerDialog.show();
    }


    private void openTimePicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {


                textView.setText(String.valueOf(hour) + ":" + String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }
}
