package com.csc.telezhnaya.currency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {
    private double currency;

    private EditText fromValue;
    private EditText toValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        currency = getIntent().getDoubleExtra(MainActivity.URL_CURRENCY, 0);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(getIntent().getStringExtra(MainActivity.URL_NAME));
        fromValue = (EditText) findViewById(R.id.from_value);
        fromValue.setText("1");
        toValue = (EditText) findViewById(R.id.to_value);
        toValue.setText(String.valueOf(currency));
    }

    public void onFromClick(View view) {
        double newValue = Double.valueOf(fromValue.getText().toString());
        toValue.setText(String.format("%.3f", newValue * currency));
    }

    public void onToClick(View view) {
        double newValue = Double.valueOf(toValue.getText().toString());
        fromValue.setText(String.format("%.3f", newValue / currency));
    }

    public void onReturnClick(View view) {
        finish();
    }
}
