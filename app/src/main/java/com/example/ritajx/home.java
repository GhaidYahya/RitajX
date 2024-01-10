package com.example.ritajx;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        recyclerView = findViewById(R.id.tasks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
        setupSlider();
        loadTasks(); // Populate the task lis

        Button servicesButton = findViewById(R.id.services_btn2);
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServicesActivity();
            }
        });



    }
    private void loadTasks() {
        // Add tasks to your list
        taskList.add(new Task("Task 1", "Time 1"));
        taskList.add(new Task("Task 2", "Time 2"));
        taskList.add(new Task("Task 1", "Time 1"));
        taskList.add(new Task("Task 2", "Time 2"));
        taskList.add(new Task("Task 1", "Time 1"));
        taskList.add(new Task("Task 2", "Time 2"));
        // ... add more tasks ...

        taskAdapter.notifyDataSetChanged();
    }


    private void openServicesActivity() {
        Intent intent = new Intent(this, services.class);
        startActivity(intent);
    }
    private void setupSlider() {
        RecyclerView sliderRecyclerView = findViewById(R.id.horizontal_recycler_view); // Make sure you have this in your home.xml
        sliderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Prepare the items list
        List<Integer> items = new ArrayList<>();
        items.add(SliderAdapter.WEATHER_TYPE); // Add weather card
        items.add(SliderAdapter.NEWS_TYPE);    // Add news card
        // Add more items if needed

        // Set the adapter
        SliderAdapter sliderAdapter = new SliderAdapter(items);
        sliderRecyclerView.setAdapter(sliderAdapter);
    }

}
