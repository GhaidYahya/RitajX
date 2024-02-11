package com.example.ritajx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class my_schedule extends AppCompatActivity {

    private TableLayout tableLayout;
    private String userID;
    private int totalCreditHours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("after showing activity", "shown");
        setContentView(R.layout.activity_my_schedule);

        tableLayout = findViewById(R.id.tableLayout);
        userID=getUserIDFromSharedPreferences();
        String apiUrl = "http://10.0.2.2:5000/getUcourses/" + userID;
        fetchDataForSchedule(apiUrl);

    }


    private void fetchDataForSchedule(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //tableLayout.removeAllViews();
                        try {
                            Log.d("Before Loop", "Preparing to process JSON array");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject courseObject = response.getJSONObject(i);

                                String courseCode = courseObject.getString("courseCode");
                                String days = courseObject.getString("days");
                                String time = courseObject.getString("time");
                                String roomNumber = courseObject.getString("roomNumber");
                                addRowToTable(courseCode, days, time, roomNumber);
                                Log.d("After Loop", "Finished processing JSON array");
                            }
                            updateTotalCreditHours();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(my_schedule.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Schedule Fetch Error", "Error fetching data: " + error.toString());
                        Toast.makeText(my_schedule.this, "Error fetching data. :(", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private TextView createTextView(String text) {
        Log.d("createtextview", "create text view probb");
        TextView textView = new TextView(this);
        textView.setText(text);
        Log.d("after sitting", "after sitting text issue");
        textView.setVisibility(View.VISIBLE);

        textView.setPadding(10, 10, 10, 10);

        return textView;
    }

    private void addRowToTable( String courseCode, String days, String time, String roomNumber) {
        TableRow row = new TableRow(this);
        Log.d("table ", "table issue");

        TextView textViewCourseID = createTextView(courseCode);
        TextView textViewDays = createTextView(days);
        TextView textViewTime = createTextView(time);
        TextView textViewRoomNumber = createTextView(roomNumber);

        row.addView(textViewCourseID);
        row.addView(textViewDays);
        row.addView(textViewTime);
        row.addView(textViewRoomNumber);
        setLeftMargin(textViewRoomNumber);
        setLeftMargin(textViewDays);
        setLeftMargin(textViewTime);
        setLeftMargin(textViewCourseID);
        int rowColor = (tableLayout.getChildCount() % 2 == 0) ? R.color.even_row_color : R.color.odd_row_color;
        row.setBackgroundColor(ContextCompat.getColor(this, rowColor));

        tableLayout.addView(row);
        totalCreditHours += 3;
    }
    private void setLeftMargin(View view) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = 10;

        view.setLayoutParams(params);
    }

    private void updateTotalCreditHours() {
        TextView totalCreditHoursTextView = findViewById(R.id.hourstextView);
        totalCreditHoursTextView.setText("Total Credit Hours: " + totalCreditHours);
    }

    private String getUserIDFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        // Return null as default value if "userID" not found
        return sharedPreferences.getString("userID", null);
    }
}
