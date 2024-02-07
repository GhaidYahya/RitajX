package com.example.ritajx;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class dentist extends AppCompatActivity {
    TextView showText, showText2;
    Button buttonTime, buttonDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist);
        showText = findViewById(R.id.showText);
        showText2 = findViewById(R.id.showText2);
        buttonDate = findViewById(R.id.buttonDate2);
        buttonTime = findViewById(R.id.buttonTime2);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogTime();
            }
        });
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showText.setText(String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(dayOfMonth));

            }
        }, 2024, 0, 15);
        dialog.show();
    }

    private void openDialogTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showText2.setText(String.valueOf(hourOfDay) + "." + String.valueOf(minute));
            }
        }, 15, 00, true);
        dialog.show();
    }
}
