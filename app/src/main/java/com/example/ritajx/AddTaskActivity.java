package com.example.ritajx;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private Button addTask;
    private EditText titleEditText;
    private EditText descEditText;
    private ArrayList<taskData> taskList;

    public static String getCurrentDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return sdf.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();
    }

    private void initViews() {
        taskList = new ArrayList<>();
        addTask = (Button) findViewById(R.id.add_task_btn);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();

                if (!desc.isEmpty() && !title.isEmpty()) {
                    Gson gson = new Gson();
                    taskData task = new taskData();

                    task.setDesc(desc);
                    task.setStatus("Due");
                    task.setTitle(title);
                    task.setDate(getCurrentDateStr());
                    taskList.add(task);


                    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

                    if (sh.getString("data", "") != "") {

                        sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String jsonString = sh.getString("data", "");

                        Type listOfMyClassObject = new TypeToken<ArrayList<taskData>>() {
                        }.getType();
                        ArrayList<taskData> outputList = gson.fromJson(jsonString, listOfMyClassObject);
                        outputList.add(task);

                        String finlList = gson.toJson(outputList);
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putString("data", finlList);
                        myEdit.commit();

                        goToMainActivity();

                    } else {

                        String jsonString = gson.toJson(taskList);

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("data", jsonString);
                        myEdit.commit();

                        goToMainActivity();

                    }


                } else {
                    Toast.makeText(AddTaskActivity.this, " Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void goToMainActivity() {
        finish();
        Intent i = new Intent(getApplicationContext(), todoM.class);
        startActivity(i);
    }
}
