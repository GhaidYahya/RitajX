package com.example.ritajx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class services extends AppCompatActivity {

    private Button gym_btn;
    private Button bank_btn;
    private Button library_btn;

    private Button clinic_btn;

    private Button mytodo_btn;


    private ImageButton navButton;
    private LinearLayout drawerLayout;
    private Animation slideInAnimation;
    private Animation slideOutAnimation;
    private boolean isDrawerOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        gym_btn = findViewById(R.id.gym_btn);
        gym_btn = findViewById(R.id.gym_btn);
        bank_btn = findViewById(R.id.bank_btn);
        library_btn = findViewById(R.id.library_btn);
        clinic_btn = findViewById(R.id.clinic_btn);
        mytodo_btn = findViewById(R.id.mytodo_btn);

        navButton = findViewById(R.id.navv_btn);
        drawerLayout = findViewById(R.id.nav_drawer);

        slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out);

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

        clinic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, clinic.class);
                startActivity(intent);
            }
        });

        library_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, Library.class);
                startActivity(intent);
            }
        });
        bank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, BankHomePage.class);
                startActivity(intent);
            }
        });


        mytodo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, todoM.class);
                startActivity(intent);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerOpen) {
                    drawerLayout.startAnimation(slideOutAnimation);
                    drawerLayout.setVisibility(View.GONE);
                } else {
                    drawerLayout.setVisibility(View.VISIBLE);
                    drawerLayout.startAnimation(slideInAnimation);
                }
                isDrawerOpen = !isDrawerOpen;
            }
        });

    }
}