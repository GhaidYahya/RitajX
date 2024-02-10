package com.example.ritajx;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class todoM extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private Button addTask;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<taskData> outputList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);

        initViews();
    }

    private void initViews() {
        addTask = (Button) findViewById(R.id.add_btn);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(i);
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String jsonString = sh.getString("data", "");

        Gson gson = new Gson();

        Type listOfMyClassObject = new TypeToken<ArrayList<taskData>>() {
        }.getType();
        outputList = gson.fromJson(jsonString, listOfMyClassObject);

        // set up the RecyclerView
        recyclerView = findViewById(R.id.taskList);
        if (outputList != null && outputList.size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyRecyclerViewAdapter(this, outputList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(View view, int position) {

        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(todoM.this, view);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Gson gson = new Gson();
                // Toast message on menu item clicked
                Toast.makeText(todoM.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                outputList.get(position).setStatus(String.valueOf(menuItem.getTitle()));

                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                String finlList = gson.toJson(outputList);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putString("data", finlList);
                myEdit.commit();

                adapter.notifyItemChanged(position);
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }
}