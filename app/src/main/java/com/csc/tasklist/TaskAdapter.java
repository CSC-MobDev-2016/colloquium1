package com.csc.tasklist;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Oleg Doronin
 * TaskList
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class TaskAdapter extends CursorAdapter {

    private MainActivity activity;
    private int paintFlags;

    TaskAdapter(Context context, Cursor cursor, MainActivity activity) {
        super(context, cursor, 0);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final LinearLayout llItemTask = (LinearLayout) view.findViewById(R.id.item_task);
        final CheckBox cbStar = (CheckBox) view.findViewById(R.id.item_star);
        final TextView tvCurrenyName = (TextView) view.findViewById(R.id.item_currency_name);
        final TextView tvCurrencyValue = (TextView) view.findViewById(R.id.item_currency_value);

        final int id = cursor.getInt(cursor.getColumnIndex(FeedsTable._ID));
        final String currency = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_CURRENCY));
        final double value = cursor.getDouble(cursor.getColumnIndex(FeedsTable.COLUMN_VALUE));
        final String date = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_DATE));
        final int star = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_STAR));
        final int priority = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_PRIORITY));


     }
}
