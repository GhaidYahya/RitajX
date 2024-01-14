package com.example.ritajx;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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


    Button profileButton;
    ImageButton profileButton2;

    Button Grades_btn;
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

        profileButton = findViewById(R.id.profile_btn);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, profile.class);
                startActivity(intent);
            }
        });

        profileButton2 = findViewById(R.id.profileSmallbtn);
        profileButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, profile.class);
                startActivity(intent);
            }
        });


        Grades_btn = findViewById(R.id.Grades_btn);
        Grades_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Grade.class);
                startActivity(intent);
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
        items.add(SliderAdapter.Birzeit_Cover);
        items.add(SliderAdapter.WEATHER_TYPE); // Add weather card
        items.add(SliderAdapter.NEWS_TYPE);    // Add news card
        items.add(SliderAdapter.Birzeit_Cover2);    // Add news card

        // Add more items if needed

        // Set the adapter
        SliderAdapter sliderAdapter = new SliderAdapter(items);
        sliderRecyclerView.setAdapter(sliderAdapter);
        sliderAdapter.setOnItemClickListener(new SliderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int viewType) {
                // Call the onItemClick method in the home activity
                home.this.onItemClick(position, viewType);
            }
        });
    }


    public void onItemClick(int position, int viewType) {
        // Handle item click based on the viewType
        if (viewType == SliderAdapter.WEATHER_TYPE) {
            // Handle news item click or Birzeit_Cover item click
            Intent intent = new Intent(home.this, weatherActivity.class);
            // Add any necessary data to the intent
            startActivity(intent);
        }
    }
}
