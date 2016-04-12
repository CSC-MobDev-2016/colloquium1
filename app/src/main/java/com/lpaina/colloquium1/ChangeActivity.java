package com.lpaina.colloquium1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        Intent intent = getIntent();
        String title = intent.getStringExtra(RVAdapter.CardViewHolder.TITLE);
        String value = intent.getStringExtra(RVAdapter.CardViewHolder.VALUE);
        final float value_float = Float.parseFloat(value.replace(",", "."));

        final EditText editText1 = (EditText) findViewById(R.id.first_value);
        final EditText editText2 = (EditText) findViewById(R.id.second_value);

        TextView textView1 = (TextView) findViewById(R.id.first_title);
        TextView textView2 = (TextView) findViewById(R.id.second_title);

        editText1.setText("1");
        editText2.setText(value);

        textView1.setText("RUB");
        textView2.setText(title);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float new_value = Float.parseFloat(String.valueOf(s));
                editText2.setText(Float.toString(value_float * new_value));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float new_value = Float.parseFloat(String.valueOf(s));
                editText1.setText(Float.toString(new_value / value_float));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
