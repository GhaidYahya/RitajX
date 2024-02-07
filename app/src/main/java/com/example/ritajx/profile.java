package com.example.ritajx;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class profile extends AppCompatActivity {

    TextView ProfileID;
    TextView ProfileUsername;
    TextView ProfileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileID = findViewById(R.id.pID);
        ProfileUsername = findViewById(R.id.pusername);
        ProfileEmail = findViewById(R.id.pEmail);

        // Retrieve userID from intent
        String userID = getIntent().getStringExtra("userID");
        fetchUserData(userID);
    }

    private void fetchUserData(String userID) {
        String url = "http://10.0.2.2:5000/profiledata/" + userID;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Extract data from JSON response
                        String username = response.getString("username");
                        String email = response.getString("email");
                        // Assuming the response contains more fields you can fetch them similarly
                        // Update the TextViews
                        ProfileID.setText(userID);
                        ProfileUsername.setText(username);
                        ProfileEmail.setText(email); // You need to modify the Flask route to return this data
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    Log.e("Profile Fetch Error", error.toString());
                });

        queue.add(jsonObjectRequest);
    }

}