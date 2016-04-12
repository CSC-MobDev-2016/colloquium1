package com.malinovsky239.currencyconverter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CurrencyListCursorAdapter extends CursorAdapter {
    public CurrencyListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvRate = (TextView) view.findViewById(R.id.rate);
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedsTable.COLUMN_NAME));
        String rate = cursor.getString(cursor.getColumnIndexOrThrow(FeedsTable.COLUMN_RATE));
        tvName.setText(name);
        tvRate.setText(rate);
    }
}
