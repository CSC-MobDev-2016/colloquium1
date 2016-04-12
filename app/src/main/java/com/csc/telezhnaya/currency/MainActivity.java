package com.csc.telezhnaya.currency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CurrencyProvider(this, getApplicationContext()).execute();
    }

    public void onRefreshClick(View view) {
        new CurrencyProvider(this, getApplicationContext()).execute();
    }
}
