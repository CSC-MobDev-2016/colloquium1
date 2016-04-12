package com.csc.shmakov.currency_converter.model;

/**
 * Created by Pavel on 4/3/2016.
 */
public interface DataChangeObserver {
    void onItemInserted(int position);
    void onItemMoved(int from, int to);
    void onItemChanged(int position);
    void onDataReset();
}
