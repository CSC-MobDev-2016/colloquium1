package com.csc.shmakov.currency_converter.activity;

import android.support.v7.widget.RecyclerView;

import com.csc.shmakov.currency_converter.model.DataChangeObserver;

/**
 * Created by Pavel on 4/3/2016.
 */
public class RecyclerViewDataChangeRouter implements DataChangeObserver {
    private final RecyclerView.Adapter<?> adapter;

    public RecyclerViewDataChangeRouter(RecyclerView.Adapter<?> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void onItemMoved(int from, int to) {
        adapter.notifyItemMoved(from, to);
    }

    @Override
    public void onItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onDataReset() {
        adapter.notifyDataSetChanged();
    }
}
