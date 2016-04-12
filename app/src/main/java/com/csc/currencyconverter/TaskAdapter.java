package com.csc.currencyconverter;

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

import com.csc.colloquium1.R;

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
        final CheckBox cbDone = (CheckBox) view.findViewById(R.id.item_task_done);
        final CheckBox cbStar = (CheckBox) view.findViewById(R.id.item_task_star);
        final TextView tvHeader = (TextView) view.findViewById(R.id.item_task_header);

        final int id = cursor.getInt(cursor.getColumnIndex(FeedsTable._ID));
        final String header = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_HEADER));
        final String body = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_BODY));
        final int done = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_DONE));
        final int star = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_STAR));
        final int color = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_COLOR));

        llItemTask.setBackgroundColor(color);
        cbDone.setChecked(done == 0 ? false : true);
        cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvHeader.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvHeader.setPaintFlags(paintFlags);
                }
                ContentValues values = new ContentValues();
                values.put(FeedsTable.COLUMN_DONE, isChecked);
                activity.getContentResolver().update(activity.ENTRIES_URI, values, FeedsTable._ID + "=" + Integer.toString(id), null);
            }
        });
        cbStar.setChecked(star == 0 ? false : true);
        cbStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                values.put(FeedsTable.COLUMN_STAR, isChecked);
                activity.getContentResolver().update(activity.ENTRIES_URI, values, FeedsTable._ID + "=" + Integer.toString(id), null);
            }
        });

        tvHeader.setText(header);
        if (!cbDone.isChecked()) {
            paintFlags = tvHeader.getPaintFlags();
        }

        if (cbDone.isChecked()) {
            tvHeader.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        /*new ChromaDialog.Builder()
                .initialColor(Color.GREEN)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(new ColorSelectListener() {
                    @Override
                    public void onColorSelected(int i) {

                    }
                })
                .create()
                .show(getSupportFragmentManager(), "ChromaDialog");*/  // 3:00 left to sleep. I went to sleep. Sorry :(

        llItemTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.header_alert));
                final LinearLayout ll = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.task_alert, null);
                builder.setView(ll);
                ((EditText) ll.findViewById(R.id.alert_header)).setText(header);
                ((EditText) ll.findViewById(R.id.alert_body)).setText(body);

                builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        String header = ((EditText) ll.findViewById(R.id.alert_header)).getText().toString();
                        String body = ((EditText) ll.findViewById(R.id.alert_body)).getText().toString();
                        values.put(FeedsTable.COLUMN_HEADER, header);
                        values.put(FeedsTable.COLUMN_BODY, body);
                        values.put(FeedsTable.COLUMN_COLOR, Color.WHITE);
                        activity.getContentResolver().update(activity.ENTRIES_URI, values, FeedsTable._ID + "=" + Integer.toString(id), null);
                    }
                });
                builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton(context.getString(R.string.remove), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.getContentResolver().delete(activity.ENTRIES_URI, FeedsTable._ID + "=" + Integer.toString(id), null);
                    }
                });

                builder.show();
            }
        });

    }
}
