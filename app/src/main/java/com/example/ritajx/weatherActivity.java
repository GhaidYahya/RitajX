package com.example.ritajx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherActivity extends AppCompatActivity {

    private String url = "https://api.weatherapi.com/v1/current.json?key=8d7110bf23a5498185b145204241101&q=Palestine&aqi=yes";

    private TextView textLastTime;
    private TextView textWindSpeed;
    private TextView textCityName;
    private TextView textTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initialize UI elements
        textCityName = findViewById(R.id.textCityName);
        textTemp = findViewById(R.id.textTemp);
        textLastTime = findViewById(R.id.textLastTime);
        textWindSpeed = findViewById(R.id.textWindSpeed);

        // Make a network request using Volley
        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a JSON object from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON response and update UI
                        parseAndDisplayData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        Log.e("Volley Error", "Error: " + error.toString());
                        // You can add error handling logic here
                        // Optional: Display an error message to the user
                        if (error.networkResponse != null) {
                            String statusCode = String.valueOf(error.networkResponse.statusCode);
                            Log.e("Status Code", statusCode);
                            Toast.makeText(weatherActivity.this, "Network error: " + statusCode, Toast.LENGTH_LONG).show();
                        } else {
                            // This block handles errors like timeout or no connection
                            Toast.makeText(weatherActivity.this, "Network error, please check your connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void parseAndDisplayData(JSONObject response) {
        try {
            // Extract data from the JSON response
            JSONObject location = response.getJSONObject("location");
            String cityName = location.getString("name");

            JSONObject current = response.getJSONObject("current");
            double temperature = current.getDouble("temp_c");
            String lastUpdated = current.getString("last_updated");
            double windSpeed = current.getDouble("wind_kph");

            // Update UI elements with the extracted data
            textCityName.setText(cityName);
            textTemp.setText(String.valueOf(temperature));
            textLastTime.setText(lastUpdated);
            textWindSpeed.setText(String.valueOf(windSpeed) + " Km/h");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
