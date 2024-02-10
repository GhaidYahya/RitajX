package com.example.ritajx;



import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button profileButton;
    ImageButton profileButton2;
    TextView hello_user;
    Button Grades_btn;
    Button schedule_btn;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    String userID;

    RequestQueue requestQueue;

    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_nav);
        recyclerView = findViewById(R.id.tasks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hello_user = findViewById(R.id.hello_user);
        drawerLayout = findViewById(R.id.drawer_nav);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
        requestQueue = Volley.newRequestQueue(this);

        ImageButton navButton = findViewById(R.id.navbtn);
        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem homeNavItem = navigationView.getMenu().findItem(R.id.nav_home);
        MenuItem serviNavItem = navigationView.getMenu().findItem(R.id.nav_servi);
        navigationView.setNavigationItemSelectedListener(this);
        navButton.setOnClickListener(v -> nav());

        // Get userID from intent
        userID = getIntent().getStringExtra("userID");
        if (userID==null){
            userID=getUserIDFromSharedPreferences();
        }

        //save userid in shared preference access it from any activity without needing to pass it through intents.
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", userID);
        editor.apply();


        //clear the tasklist to avoid duplicate items when log in
        if (taskList != null) {
            taskList.clear();
            taskAdapter.notifyDataSetChanged();
            fetchBookings("Gym", Integer.parseInt(userID), "getGymBookings", "bookingTime", "bookingID");
            fetchBookings("Library", Integer.parseInt(userID), "getLibraryBookings", "bookingTime", "bookingID");
            fetchBookings("Clinic", Integer.parseInt(userID), "getClinicBookings", "appointmentTime", "appointmentID");
            fetchBookings("Bank", Integer.parseInt(userID), "getBankBookings", "appointmentTime", "appointmentID");
            setupRecyclerView();
        }
        fetchUsername(userID);
        setupSlider();

        Button servicesButton = findViewById(R.id.services_btn2);
        servicesButton.setOnClickListener(v -> openServicesActivity());

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
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    //to handle duplicates of recycler items
    @Override
    protected void onRestart() {
        super.onRestart();
        if (taskList != null) {
            taskList.clear();
            taskAdapter.notifyDataSetChanged();
            fetchBookings("Gym", Integer.parseInt(userID), "getGymBookings", "bookingTime", "bookingID");
            fetchBookings("Library", Integer.parseInt(userID), "getLibraryBookings", "bookingTime", "bookingID");
            fetchBookings("Clinic", Integer.parseInt(userID), "getClinicBookings", "appointmentTime", "appointmentID");
            fetchBookings("Bank", Integer.parseInt(userID), "getBankBookings", "appointmentTime", "appointmentID");
        }
    }


    private void nav() {
        if (drawerLayout != null) {
            Log.d("DrawerStatus", "Drawer is not null, opening...");
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            Log.e("DrawerStatus", "Drawer is null!");
        }
    }


    //to handle duplicates of recycler items
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (taskList != null) {
            taskList.clear();
            taskAdapter.notifyDataSetChanged();
        }
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


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_nav);

        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        hello_user.setText("Hello, " + username +" ♡");
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



    //
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_nav);

        switch (item.getTitle().toString()) {
            case "Home":
                drawer.closeDrawer(GravityCompat.START);
                break;

            case "Services":
                openServicesActivity();
                break;

            case "Settings":
                Intent profileIntent = new Intent(home.this, profile.class);
                startActivity(profileIntent);

                break;

            case "Share with friends":
                String appLink = "http://RitajX_Downaload_App";
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("http://RitajX_Downaload_App", appLink);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "RitajX link copied to clipboard :)", Toast.LENGTH_SHORT).show();


                break;
            case "About Us":
                Intent aboutusIntent = new Intent(home.this, AboutUs.class);
                startActivity(aboutusIntent);

                break;

            case "My Schedule":


                break;
            case "My Grades":
                Intent gradesIntent = new Intent(home.this, Grade.class);
                startActivity(gradesIntent);


                break;
            case "Weather":
                Intent weatherIntent = new Intent(home.this, weatherActivity.class);
                startActivity(weatherIntent);

                break;
            case "Currency Exchange":
                Intent bankIntent = new Intent(home.this, Bank2.class);
                startActivity(bankIntent);

                break;
            case "BMI":
                Intent mbiIntent = new Intent(home.this, BMI.class);
                startActivity(mbiIntent);


                break;

            case "Logout":
                Intent logOutIntent = new Intent(home.this, MainActivity.class);
                startActivity(logOutIntent);
                finish();

                break;

            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fetchBookings(String type, int userId, String url, String timeKey, String idKey) {
        String fullUrl = "http://10.0.2.2:5000/" + url + "/" + userId;



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, fullUrl, null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject booking = response.getJSONObject(i);
                    String bookingTime = booking.getString(timeKey);
                    int bookingId = booking.getInt(idKey);

                    Task bookingTask = new Task(type + " Booking", bookingTime, type, bookingId);
                    taskList.add(bookingTask);
                }
                taskAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(type + "Bookings", "Error parsing " + type.toLowerCase() + " bookings", e);
            }
        }, error -> {
            Log.e(type + "Bookings", "Error fetching " + type.toLowerCase() + " bookings", error);
        });

        requestQueue.add(jsonArrayRequest);
    }



    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.tasks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // We don't want to handle move in this example.
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return; // Early return if position is not valid
                }
                Task task = taskList.get(position);
                int bookingId = task.getBookingID(); // Make sure this method exists in your Task class
                String taskType = task.getTaskType();

                // Determine the correct URL for the delete operation based on the task type
                String url;
                switch (taskType) {
                    case "Gym":
                        url = "http://10.0.2.2:5000/deleteGymBooking";
                        break;
                    case "Library":
                        url = "http://10.0.2.2:5000/deleteLibraryBooking";
                        break;
                    case "Clinic":
                        url = "http://10.0.2.2:5000/deleteClinicBooking";
                        break;
                    case "Bank":
                        url = "http://10.0.2.2:5000/deleteBankBooking";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + taskType);
                }

                // Prepare the JSON payload for the POST request.
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("bookingId", bookingId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create a Volley request.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, response -> {
                    // Handle the successful deletion in the database.
                    taskList.remove(position);
                    taskAdapter.notifyItemRemoved(position);
                    Toast.makeText(home.this, taskType + " booking deleted", Toast.LENGTH_SHORT).show();
                }, error -> {
                    // Handle error.
                    Toast.makeText(home.this, "Failed to delete " + taskType.toLowerCase() + " booking", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                // Add the request to the Volley request queue.
                requestQueue.add(jsonObjectRequest);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                // Optional: Add background color or icon when swiping.
            }
        };

        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recyclerView);
    }



    private String getUserIDFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        // Return null as default value if "userID" not found
        return sharedPreferences.getString("userID", null);
    }
}


