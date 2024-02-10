package com.example.ritajx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    private Button registerButton;
    private EditText id;
    private EditText pass;
    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginbtn);
        registerButton = findViewById(R.id.registerbutton);
        id = findViewById(R.id.ID);
        pass = findViewById(R.id.password);
        remember = findViewById(R.id.remember);

//        loadCredentials();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = id.getText().toString();
                final String password = pass.getText().toString();
                performLogin(username, password);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, registration.class);
                startActivity(intent);
            }
        });

    }

    public void profile(View view) {
        Intent intent = new Intent(MainActivity.this, home.class);
        startActivity(intent);
    }

    private void loadCredentials() {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isRemembered = prefs.getBoolean("RememberMe", false);
        if (isRemembered) {
            String username = prefs.getString("Username", "");
            String password = prefs.getString("Password", "");
            id.setText(username);
            pass.setText(password);
            remember.setChecked(true);
        }
    }


    private void performLogin(String username, String password) {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false); // Optional: make the dialog non-cancelable if you wish
        progressDialog.show();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/login") // Use the login endpoint
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Login failed: Network error", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(MainActivity.this, home.class);
                            intent.putExtra("userID", id.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed: Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login failed: Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                });
                response.close();
            }
        });
        boolean rememberThis = remember.isChecked(); // Get the checkbox state

        // Save credentials if remember is checked
        if (rememberThis) {
            SharedPreferences.Editor editor = getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("RememberMe", true);
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.apply();
        } else {
            // Clear the preferences if remember is not checked
            SharedPreferences.Editor editor = getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit();
            editor.remove("RememberMe");
            editor.remove("Username");
            editor.remove("Password");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCredentials();
    }

}
