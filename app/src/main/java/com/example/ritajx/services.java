package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class services extends AppCompatActivity {

    private Button gym_btn;
    private Button bank_btn;
    private Button library_btn;

    private Button clinic_btn;

    private Button mytodo_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        gym_btn= findViewById(R.id.gym_btn);
        gym_btn = findViewById(R.id.gym_btn);
        bank_btn = findViewById(R.id.bank_btn);
        library_btn = findViewById(R.id.library_btn);
        clinic_btn = findViewById(R.id.clinic_btn);
        mytodo_btn = findViewById(R.id.mytodo_btn);

        // Set background colors
        gym_btn.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
        bank_btn.setBackgroundTintList(getResources().getColorStateList(R.color.sage_green));
        library_btn.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        clinic_btn.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
        mytodo_btn.setBackgroundTintList(getResources().getColorStateList(R.color.lav));
        gym_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, Gymdashboard.class);
                startActivity(intent);
            }
        });
    }
}