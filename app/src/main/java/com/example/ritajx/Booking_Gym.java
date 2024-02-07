package com.example.ritajx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Booking_Gym extends AppCompatActivity {

    Date date;
    Button btnadd;
    DatePicker datePicker;
    TimePicker timePicker;

    //    private final int gymCapacity=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_gym);
        btnadd = findViewById(R.id.btnAdd);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        // Assuming there's a button to finalize the booking, set its onClickListener here
        // For example:


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 8) {
                    timePicker.setHour(8);
                    timePicker.setMinute(0);
                } else if (hourOfDay > 16 || (hourOfDay == 16 && minute > 0)) {
                    timePicker.setHour(16);
                    timePicker.setMinute(0);
                } else {
                    // Adjust minutes to nearest 0 or 30
                    if (minute < 15) {
                        timePicker.setMinute(0);
                    } else if (minute < 45) {
                        timePicker.setMinute(30);
                    } else {
                        // If the minute is beyond 45, increment the hour if it's before the cutoff
                        if (hourOfDay < 16) {
                            timePicker.setHour(hourOfDay + 1);
                            timePicker.setMinute(0);
                        } else {
                            // If it's in the last permissible hour and minutes go beyond 45, set to 30
                            timePicker.setMinute(30);
                        }
                    }
                }
            }
        });


        btnadd.setOnClickListener(v -> {
            try {
                SharedPreferences prefs = getSharedPreferences("userid", MODE_PRIVATE);
                String userId = prefs.getString("userID", "");

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1; // Month is 0-based, add 1 for correct month
                int day = datePicker.getDayOfMonth();

                // Correctly format the date and time
                String bookingTime = String.format("%d-%02d-%02d %02d:%02d:00", year, month, day, hour, minute);


                JSONObject bookingDetails = new JSONObject();
                bookingDetails.put("userID", userId);
                bookingDetails.put("bookingTime", bookingTime);

                sendBookingToServer(bookingDetails.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Booking_Gym.this, "Error in booking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendBookingToServer(String json) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/bookgym") // Ensure this is the correct URL for your Flask app
                .post(body)
                .build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                // Convert the response body to a string
                String responseBody = response.body().string();
                // Parse the response body to get the message
                JSONObject jsonResponse = new JSONObject(responseBody);
                String message = jsonResponse.getString("message");

                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(Booking_Gym.this, message, Toast.LENGTH_SHORT).show());
                } else {
                    // If not successful, handle accordingly (e.g., capacity reached)
                    runOnUiThread(() -> Toast.makeText(Booking_Gym.this, message, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Booking_Gym.this, "Failed to book", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


}
