package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.List;

public class Grade extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterGrade adapter;
    private List<String> myDataset; // Your data source
    private AppCompatImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade); // Replace with your layout name

        backButton = findViewById(R.id.backButton); // Make sure this ID matches your layout
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the home activity
                Intent intent = new Intent(Grade.this, home.class);
                startActivity(intent);

                // Optionally, if you want to finish the current Activity
                finish();
            }
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.gradesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your dataset
        myDataset = new ArrayList<>();
        myDataset.add("comp231");
        myDataset.add("comp443");

        adapter = new AdapterGrade(myDataset);
        recyclerView.setAdapter(adapter);
    }
}