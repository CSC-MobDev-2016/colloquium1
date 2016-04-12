package ru.csc.roman_fedorov.converter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by roman on 12.04.2016.
 */
public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTV = (TextView) view.findViewById(R.id.currency_name);
        TextView valueTV = (TextView) view.findViewById(R.id.currency_value);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(CurrencyTable.CURRENCY_NAME));
        String value = cursor.getString(cursor.getColumnIndexOrThrow(CurrencyTable.CURRENCY_VALUE));

        nameTV.setText(name);
        valueTV.setText(value);
    }
}
