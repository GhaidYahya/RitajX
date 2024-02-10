package com.example.ritajx;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class services extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private Button gym_btn;
    private Button bank_btn;
    private Button library_btn;

    private Button clinic_btn;

    private Button mytodo_btn;
    ImageButton profileButton2;


    private ImageButton navButton;
    DrawerLayout drawerLayout;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_nav2);
        gym_btn = findViewById(R.id.gym_btn);
        gym_btn = findViewById(R.id.gym_btn);
        bank_btn = findViewById(R.id.bank_btn);
        library_btn = findViewById(R.id.library_btn);
        clinic_btn = findViewById(R.id.clinic_btn);
        mytodo_btn = findViewById(R.id.mytodo_btn);

        navButton = findViewById(R.id.navv_btn);
        drawerLayout = findViewById(R.id.drawer_nav);




        // Set background colors
        gym_btn.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
        bank_btn.setBackgroundTintList(getResources().getColorStateList(R.color.sage_green));
        library_btn.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        clinic_btn.setBackgroundTintList(getResources().getColorStateList(R.color.pink));


        mytodo_btn.setBackgroundTintList(getResources().getColorStateList(R.color.lav));
        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem homeNavItem = navigationView.getMenu().findItem(R.id.nav_home);
        MenuItem serviNavItem = navigationView.getMenu().findItem(R.id.nav_servi);
        navigationView.setNavigationItemSelectedListener(this);

        userID = getIntent().getStringExtra("userID");
        if (userID==null){
            userID=getUserIDFromSharedPreferences();
        }
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nav();
            }
        });



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

        profileButton2 = findViewById(R.id.profile_Small_btn);
        profileButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(services.this, profile.class);
                intent.putExtra("userID", userID); // Assuming userID is a string containing the user's ID
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



    }

    private void nav() {
        if (drawerLayout != null) {
            Log.d("DrawerStatus", "Drawer is not null, opening...");
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            Log.e("DrawerStatus", "Drawer is null!");
        }
    }
    private void openHomeActivity() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_nav);

        switch (item.getTitle().toString()) {
            case "Home":
                openHomeActivity();
                break;

            case "Services":
                drawer.closeDrawer(GravityCompat.START);
                break;

            case "Settings":
                Intent profileIntent = new Intent(services.this, profile.class);
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
                Intent aboutusIntent = new Intent(services.this, AboutUs.class);
                startActivity(aboutusIntent);

                break;

            case "My Schedule":


                break;
            case "My Grades":
                Intent gradesIntent = new Intent(services.this, Grade.class);
                startActivity(gradesIntent);


                break;
            case "Weather":
                Intent weatherIntent = new Intent(services.this, weatherActivity.class);
                startActivity(weatherIntent);

                break;
            case "Currency Exchange":
                Intent bankIntent = new Intent(services.this, Bank2.class);
                startActivity(bankIntent);

                break;
            case "BMI":
                Intent mbiIntent = new Intent(services.this, BMI.class);
                startActivity(mbiIntent);


                break;

            case "Logout":
                Intent logOutIntent = new Intent(services.this, MainActivity.class);
                startActivity(logOutIntent);
                finish();

                break;

            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private String getUserIDFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        // Return null as default value if "userID" not found
        return sharedPreferences.getString("userID", null);
    }

}