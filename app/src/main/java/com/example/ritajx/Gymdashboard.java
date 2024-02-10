package com.example.ritajx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Gymdashboard extends AppCompatActivity {
    private Button btnBooking;
    Button btnReturnBack;
    private Button btnBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymdashboard);
        btnBooking = findViewById(R.id.btnBooking);
        btnBMI = findViewById(R.id.btnBMI);
        btnReturnBack= findViewById(R.id.btnReturnBack);

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.gymrizz, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.gym, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bsket, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pool, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        imageSlider.startSliding(2500);


        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Booking_Gym activity
                Intent intent = new Intent(Gymdashboard.this, Booking_Gym.class);
                startActivity(intent);
            }
        });
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Booking_Gym activity
                Intent intent = new Intent(Gymdashboard.this, BMI.class);
                startActivity(intent);
            }
        });
        btnReturnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, services.class);
            startActivity(intent);
        });
    }
}