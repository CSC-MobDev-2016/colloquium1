package com.malinovsky239.currencyconverter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final String order = FeedsTable.COLUMN_RATE;
    private CurrencyListCursorAdapter currencyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateCurrencyRates();
        ListView listCurrencies = (ListView) findViewById(R.id.listCurrencies);
        Cursor cursor = getContentResolver().query(Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries"), new String[]{}, null, new String[]{}, order);
        currencyListAdapter = new CurrencyListCursorAdapter(this, cursor);
        listCurrencies.setAdapter(currencyListAdapter);
    }

    protected void updateCurrencyRates() {
        AsyncRSSPageAccess downloadXML = new AsyncRSSPageAccess(this);
        try {
            downloadXML.execute(new URL(getString(R.string.rubExchangeRate)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addToContentProvider(MatrixCursor xmlValues) {
        for (xmlValues.moveToFirst(); !xmlValues.isAfterLast(); xmlValues.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put(FeedsTable.COLUMN_NAME, xmlValues.getString(0));
            SimpleDateFormat format = new SimpleDateFormat(getString(R.string.datetimeFormat), Locale.getDefault());
            values.put(FeedsTable.COLUMN_DATETIME, xmlValues.getString(1));
            values.put(FeedsTable.COLUMN_RATE, xmlValues.getString(2));
            getContentResolver().insert(Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries"), values);
        }
        Cursor cursor = getContentResolver().query(Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries"), new String[]{}, null, new String[]{}, order);
        currencyListAdapter.changeCursor(cursor);
    }
}
