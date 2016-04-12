package com.csc.shmakov.currency_converter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.csc.colloquium1.R;
import com.csc.shmakov.currency_converter.model.Currency;
import com.csc.shmakov.currency_converter.model.Model;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final Model model = Model.INSTANCE;

    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        model.bindToContext(this, new RecyclerViewDataChangeRouter(adapter));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.unbindFromContext();
    }

    public void onUpdateClick(View view) {
        model.update();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name_textview) TextView nameTextView;
        @Bind(R.id.value_textview) TextView valueTextView;

//        @Bind(R.id.done_checkbox) CheckBox doneCheckBox;

        private int position;
        private Currency item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position) {
            this.position = position;
            item = model.getItem(position);

            updateViews();
        }

        private void updateViews() {
            nameTextView.setText(String.valueOf(item.name));
            valueTextView.setText(String.valueOf(item.value));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CalcActivity.class);
                    intent.putExtra(CalcActivity.EXTRA_ITEM_POSITION, position);
                    startActivity(intent);
                }
            });
        }

    }

    private final RecyclerView.Adapter<ViewHolder> adapter = new RecyclerView.Adapter<ViewHolder>() {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return model.getSize();
        }
    };
}
