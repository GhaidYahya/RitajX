package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registerButton = findViewById(R.id.button);
        registerButton.setOnClickListener(v -> {
            EditText userid =findViewById(R.id.userid);
            EditText usernameField = findViewById(R.id.username);
            EditText email = findViewById(R.id.email);
            EditText pass = findViewById(R.id.pass);
            EditText confirmPass = findViewById(R.id.confirmPass);
            // Assuming editTextText2 is for password confirmation, ensure both passwords match before proceeding

            String userID = userid.getText().toString();
            String username = usernameField.getText().toString();
            String stremail = email.getText().toString();
            String password = pass.getText().toString();
            String confirmpassword = confirmPass.getText().toString();
            // Add validation here if necessary

            if (password.equals(confirmpassword)){
                registerUser(userID, username, stremail, password);
                userid.setText("");
                usernameField.setText("");
                email.setText("");
                pass.setText("");
                confirmPass.setText("");
            }


        });
    }


    private void registerUser(String id, String username, String email, String pass) {
        String registerUrl = "http://10.0.2.2:5000/register";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("userID", id);
            postData.put("username", username);
            postData.put("email", email);
            postData.put("password", pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerUrl, postData, response -> {
            // Handle response
            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(registration.this, MainActivity.class);
        }, error -> {
            // Handle error
            if(error.networkResponse != null){
                String body;
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Toast.makeText(getApplicationContext(), "Registration Failed: " + body, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Registration Failed. No response from the server.", Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

}