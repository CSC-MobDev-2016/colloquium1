package com.csc.shmakov.currency_converter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.csc.colloquium1.R;
import com.csc.shmakov.currency_converter.model.Currency;
import com.csc.shmakov.currency_converter.model.Model;

import junit.framework.Assert;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalcActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM_POSITION = "EXTRA_ITEM_POSITION";

    private static Model model = Model.INSTANCE;

    @Bind(R.id.item_ru_edit_text)
    EditText rubEditText;
    @Bind(R.id.item_other_edit_text)
    EditText otherEditText;
    @Bind(R.id.item_other_name)
    TextView nameTextView;

    private int position;
    private float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        ButterKnife.bind(this);

        position = getIntent().getIntExtra(EXTRA_ITEM_POSITION, -1);
        Assert.assertFalse(position == -1);


        Currency item = model.getItem(position);
        rate = item.value;
        nameTextView.setText(item.name);

        rubEditText.addTextChangedListener(rubTextWatcher);
        otherEditText.addTextChangedListener(otherTextWatcher);
    }

    private final TextWatcher otherTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                float input = Float.valueOf(s.toString());
                float result = input / rate;
                rubEditText.removeTextChangedListener(rubTextWatcher);
                rubEditText.setText(String.valueOf(result));
                rubEditText.addTextChangedListener(rubTextWatcher);
            } catch (NumberFormatException e) {
                // Whatever
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher rubTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                float input = Float.valueOf(s.toString());
                float result = input * rate;
                otherEditText.removeTextChangedListener(otherTextWatcher);
                otherEditText.setText(String.valueOf(result));
                otherEditText.addTextChangedListener(otherTextWatcher);
            } catch (NumberFormatException e) {
                // Whatever
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
