package com.example.ritajx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Grade extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterGrade adapter;
    private ImageView backButton;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade); // Replace with your layout name

        backButton = findViewById(R.id.backButton); // Make sure this ID matches your layout
        //fetch the userID from the shered preference if its null
        if (userID==null)
            userID = getUserIDFromSharedPreferences();
        // Now you can fetch grades
        fetchGrades();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the home activity
                Intent intent = new Intent(Grade.this, home.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.gradesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterGrade(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }
    public void fetchGrades() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:5000/getGrades?userID=" + userID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            List<GradeObject> grades = new ArrayList<>();
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject gradeObject = response.getJSONObject(i);
                    GradeObject grade = new GradeObject(
                            gradeObject.getString("courseName"),
                            gradeObject.getString("gradeDescription"),
                            gradeObject.getString("gradeType"),
                            gradeObject.getString("gradeValue")
                    );
                    grades.add(grade);
                }
                adapter.updateGrades(grades);
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle error
            }
        }, error -> {
            // Handle error
        });

        queue.add(jsonArrayRequest);
    }


    private String getUserIDFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        // Return null as default value if "userID" not found
        return sharedPreferences.getString("userID", null);
    }



}