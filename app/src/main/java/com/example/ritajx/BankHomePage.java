package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BankHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_home_page);
        Button Booking = findViewById(R.id.booking);
        Button convert = findViewById(R.id.convert);

        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BankHomePage.this,Bank3.class);
                startActivity(intent1);
            }
        });
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BankHomePage.this,Bank2.class);
                startActivity(intent1);
            }
        });


    }

}
