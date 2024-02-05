package com.example.ritajx;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;


    Button profileButton;
    ImageButton profileButton2;

    TextView hello_user;
    Button Grades_btn;
    Button schedule_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        recyclerView = findViewById(R.id.tasks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hello_user = findViewById(R.id.hello_user);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        // Get userID from intent
        String userID = getIntent().getStringExtra("userID");

        // Fetch username from the database
        fetchUsername(userID);
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
                intent.putExtra("userID", userID); // Assuming userID is a string containing the user's ID
                startActivity(intent);
            }
        });

        profileButton2 = findViewById(R.id.profileSmallbtn);
        profileButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, profile.class);
                intent.putExtra("userID", userID); // Assuming userID is a string containing the user's ID
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

        schedule_btn = findViewById(R.id.schedule_btn);
        schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, my_schedule.class);
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

    private void fetchUsername(String userID) {
        String fetchUrl = "http://10.0.2.2:5000/getuser/" + userID; // Adjust the URL as needed
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("Request URL", fetchUrl);  // Use this to debug the actual URL being called


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fetchUrl, null,
                response -> {
                    try {
                        // Assuming the response has a field named "username"
                        String username = response.getString("username");
                        // Update your text view here with the fetched username
                        hello_user.setText("Hello " + username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error fetching username", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "JSON Parse Error!", Toast.LENGTH_SHORT).show();
                    }
                });


                queue.add(jsonObjectRequest);
    }

}


