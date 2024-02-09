package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Library extends AppCompatActivity {

    private EditText editTextStudentId;
    private Button saveButton;
    private TextView textView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        editTextStudentId = findViewById(R.id.editTextStudentId);
        saveButton = findViewById(R.id.Save);
        textView = findViewById(R.id.show);

        calendar = Calendar.getInstance();

        Button clickButton = findViewById(R.id.click);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
                openTimePicker();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTime = textView.getText().toString();
                // Split the dateTime string into date and time
                String[] dateTimeArray = dateTime.split(" ");
                String date = dateTimeArray[0];
                String time = dateTimeArray[1];
                Spinner availableRoomsSpinner = findViewById(R.id.available_rooms);
                String roomID = availableRoomsSpinner.getSelectedItem().toString();
                Spinner bookingDurationSpinner = findViewById(R.id.bookingduration);
                String selectedDuration = bookingDurationSpinner.getSelectedItem().toString();
                String duration = selectedDuration.split(" ")[0];

                Spinner numberOfPeopleSpinner = findViewById(R.id.NumberOfPeople);
                String numStudent = numberOfPeopleSpinner.getSelectedItem().toString();

                saveBooking(date, time, roomID, numStudent, duration);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
                openTimePicker();
            }
        });
    }

    private void openDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateTime();
            }
        }, year, month, day);

        datePickerDialog.show();
    }


    private void openTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                updateDateTime();
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    private void updateDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
        String dateTime = dateFormat.format(calendar.getTime());
        textView.setText(dateTime);
    }

    private void saveBooking(String date, String time, String roomID, String numStudent, String duration) {
        new Thread(() -> {
            try {
                // Construct the booking information JSON
                JSONObject bookingInfo = new JSONObject();
                bookingInfo.put("userID", editTextStudentId.getText().toString());
                bookingInfo.put("bookingTime", date + " " + time);
                bookingInfo.put("roomID", roomID);
                bookingInfo.put("numStudent", numStudent);
                bookingInfo.put("duration", duration);
                // Setup the connection to your backend
                URL url = new URL("http://10.0.2.2:5000/booking");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                // Send the JSON data
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(bookingInfo.toString());
                os.flush(); // Ensure data is sent immediately
                os.close();

                // Handle the response from the server
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) { // Success
                    runOnUiThread(() -> Toast.makeText(Library.this, "Booking saved successfully", Toast.LENGTH_LONG).show());
                } else { // Server returned an error
                    runOnUiThread(() -> Toast.makeText(Library.this, "Failed to save booking", Toast.LENGTH_LONG).show());
                }
                conn.disconnect();
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Library.this, "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Library.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}