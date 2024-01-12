package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

public class Booking_Gym extends AppCompatActivity {
    String message="The University Will be Closed u idiot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_gym);

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setHour(20); // Set the starting hour
        timePicker.setMinute(0); // Set the starting minute

        //when to button is finished this shit will be took
        if (timePicker.getHour()>16 && timePicker.getHour() < 7){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

// Restrict the end time to 4:00 PM
        int endHour = 16;
        timePicker.setHour(endHour);
        timePicker.setMinute(0);

// Implement custom minute intervals (e.g., 30 minutes)
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            if (minute % 30 != 0) {
                // Round the minute to the nearest 30
                timePicker.setMinute((minute / 30) * 30);
            }
        });
    }
}