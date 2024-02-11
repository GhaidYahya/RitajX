package com.example.ritajx;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Bank2 extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner fromCurrencySpinner, toCurrencySpinner;
    private Button convertButton, backButton;
    private TextView resultTextView;
    private RequestQueue requestQueue;
    String url = "https://v6.exchangerate-api.com/v6/0497107ca4bca5d8c8cd3ac2/latest/USD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank2);

        amountEditText = findViewById(R.id.amountEditText);
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        backButton = findViewById(R.id.button6);

        requestQueue = Volley.newRequestQueue(this);

        fetchCurrencies();

        convertButton.setOnClickListener(view -> convertCurrency());

        backButton.setOnClickListener(view -> finish());
    }

    private void fetchCurrencies() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("conversion_rates");
                        ArrayList<String> currencies = new ArrayList<>();
                        Iterator<String> keys = rates.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            currencies.add(key);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        fromCurrencySpinner.setAdapter(adapter);
                        toCurrencySpinner.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        resultTextView.setText("Failed to fetch currencies");
                    }
                }, error -> resultTextView.setText("Error fetching currency data"));

        requestQueue.add(jsonObjectRequest);
    }

    private void convertCurrency() {
        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();
        String amountText = amountEditText.getText().toString();

        if (amountText.isEmpty()) {
            resultTextView.setText("Please enter an amount to convert");
            return;
        }

        double amount = Double.parseDouble(amountText);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("conversion_rates");
                        double rate = rates.getDouble(toCurrency);
                        double result = amount * rate;
                        resultTextView.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency));
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultTextView.setText("Conversion error");
                    }
                }, error -> resultTextView.setText("Error during conversion"));

        requestQueue.add(jsonObjectRequest);
    }
}