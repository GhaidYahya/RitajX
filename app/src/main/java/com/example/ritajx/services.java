package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class services extends AppCompatActivity {

    private Button gym_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        gym_btn= findViewById(R.id.gym_btn);

        gym_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, Gymdashboard.class);
                startActivity(intent);
            }
        });
    }
}