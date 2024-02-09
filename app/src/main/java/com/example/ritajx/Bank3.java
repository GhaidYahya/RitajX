package com.example.ritajx;

import android.os.Bundle;
import android.view.View;
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

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
public class Bank3 extends AppCompatActivity {
    Date date;
    Button btnadd;
    DatePicker datePicker;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank3);

        btnadd=findViewById(R.id.btnAdd);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);




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
                bookingDetails.put("appointmentTime", bookingTime);

                sendBookingToServer(bookingDetails.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Bank3.this, "Error in booking", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendBookingToServer(String json) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/addBank_appointment") // Make sure this URL is correct

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
                    runOnUiThread(() -> Toast.makeText(Bank3.this, message, Toast.LENGTH_SHORT).show());
                } else {
                    // If not successful, handle accordingly (e.g., capacity reached)
                    runOnUiThread(() -> Toast.makeText(Bank3.this, message, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Bank3.this, "Failed to book", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
