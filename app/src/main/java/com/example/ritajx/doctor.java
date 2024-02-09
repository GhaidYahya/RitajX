// Import necessary packages
package com.example.ritajx;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// DoctorActivity class, extending AppCompatActivity
public class doctor extends AppCompatActivity {
    // Declare UI elements
    TextView showText2;
    Button buttonDate, buttonTime, set;
    Calendar selectedDateTime = Calendar.getInstance();

    // onCreate method to initialize the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_doctor);

        // Initialize UI elements by finding them in the layout
        showText2 = findViewById(R.id.showText2);
        buttonDate = findViewById(R.id.buttonDate2);
        buttonTime = findViewById(R.id.buttonTime2);
        set = findViewById(R.id.done);

        // Set click listeners for date, time, and set buttons
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

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    // Method to open date picker dialog
    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDateTime.set(Calendar.YEAR, year);
                selectedDateTime.set(Calendar.MONTH, month);
                selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        }, 2024, 0, 15);
        dialog.show();
    }

    // Method to open time picker dialog
    private void openDialogTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Set seconds to 00
                int seconds = 0;

                // Adjust the selected minute to be either 0 or 30
                minute = (minute < 30) ? 0 : 30;

                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDateTime.set(Calendar.MINUTE, minute);
                selectedDateTime.set(Calendar.SECOND, seconds);

                // Update the displayed time
                printSelectedDateTime();
            }
        }, 15, 0, true) {
            // Override the onTimeChanged method to limit minute choices to 0 or 30
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                super.onTimeChanged(view, hourOfDay, minute);

                // Adjust the minute values in the TimePicker
                if (minute < 15) {
                    view.setCurrentMinute(0);
                } else if (minute < 45) {
                    view.setCurrentMinute(30);
                } else {
                    view.setCurrentMinute(0);
                    view.setCurrentHour(hourOfDay + 1);
                }
            }
        };

        // Set initial time (15:00), 24-hour format
        dialog.show();
    }
    // Method to display selected date and time
    private void printSelectedDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = sdf.format(selectedDateTime.getTime());
        showText2.setText(formattedDateTime);
    }


    // Method to handle booking appointment for the doctor
    private void bookAppointment() {
        // Format the selected date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = sdf.format(selectedDateTime.getTime());

        // Retrieve user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String userID = prefs.getString("Username", ""); // Replace "Username" with the actual key

        // Check if the user ID is not empty
        if (!userID.isEmpty()) {
            // Check if the appointment time is available
            checkAppointmentAvailability(userID, formattedDateTime);
        } else {
            // Handle the case where user ID is not available
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to check appointment availability for the doctor
    private void checkAppointmentAvailability(String userID, String formattedDateTime) {
        // TODO: Implement the logic to check if the appointment time is available for the doctor
        // You may need to make a network request to your Flask server to check availability

        // For demonstration purposes, let's assume the availability check is successful
        boolean isAvailable = true;

        // If the appointment time is available
        if (isAvailable) {
            // The appointment time is available, proceed to store data in the database
            JSONObject appointmentDetails = new JSONObject();
            try {
                appointmentDetails.put("userID", userID);
                appointmentDetails.put("formattedDateTime", formattedDateTime);
                appointmentDetails.put("isDoctor", 1); // Indicate that it's a doctor appointment
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Execute AsyncTask to send appointment details to the server
            new SendAppointmentTask().execute(appointmentDetails.toString());
        } else {
            // The appointment time is not available, show a message to the user
            Toast.makeText(this, "Time not available for doctor. Please pick another time.", Toast.LENGTH_SHORT).show();
        }
    }

    // AsyncTask to send appointment details
    private class SendAppointmentTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            // Extract appointment details from the parameters
            String jsonAppointmentDetails = params[0];
            OkHttpClient client = new OkHttpClient();

            // Replace the URL with the actual endpoint for booking appointments
            String url = "http://10.0.2.2:5000//bookDoctorAppointment";

            try {
                // Create a request body with JSON data
                RequestBody body = RequestBody.create(jsonAppointmentDetails, MediaType.parse("application/json; charset=utf-8"));
                // Create a POST request
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                // Execute the request and get the response
                Response response = client.newCall(request).execute();

                // Log the response for debugging
                Log.d("SendAppointmentTask", "Response: " + response.body().string());

                // Check the HTTP status code to determine success or failure
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                // Log an error message in case of an exception
                Log.e("SendAppointmentTask", "Error sending appointment details: " + e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            // Update the UI based on the result
            updateUIAfterBooking(isSuccess);
        }
    }

    // Method to update UI based on the result
    private void updateUIAfterBooking(boolean isSuccess) {
        // If the appointment is booked successfully
        if (isSuccess) {
            Toast.makeText(doctor.this, "Picked successfully.", Toast.LENGTH_SHORT).show();
        } else {
            // The appointment time is not available, show a message to the user
            Toast.makeText(doctor.this, "Time not available for doctor. Please pick another time.", Toast.LENGTH_SHORT).show();
        }
    }
}
