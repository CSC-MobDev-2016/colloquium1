package com.csc.telezhnaya.currency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csc.telezhnaya.currency.database.CurrencyTable;
import com.csc.telezhnaya.currency.database.MyContentProvider;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(MyContentProvider.CONTENT_URI, "entries");
    public static final String DB_ORDER = CurrencyTable.COLUMN_CURRENCY_NAME;
    private CursorAdapter adapter;
    private SharedPreferences preferences;

    public static final String LAST_UPDATE = "LAST_UPDATE";

    public static final String URL_NAME = "NAME";
    public static final String URL_CURRENCY = "CURRENCY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            preferences = getSharedPreferences("currency", Context.MODE_PRIVATE);
            updateData();

            ListView listTasks = (ListView) findViewById(R.id.list);
            adapter = new CursorAdapter(this, null, 0) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return LayoutInflater.from(context).inflate(R.layout.currency, parent, false);
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    double value = cursor.getDouble(cursor.getColumnIndex(CurrencyTable.COLUMN_RATE));
                    TextView from = (TextView) view.findViewById(R.id.from_value);
                    from.setText(String.format("%.3f", 1. / value));
                    TextView name = (TextView) view.findViewById(R.id.name);
                    name.setText(cursor.getString(cursor.getColumnIndex(CurrencyTable.COLUMN_CURRENCY_NAME)));
                    TextView to = (TextView) view.findViewById(R.id.to_value);
                    to.setText(String.format("%.3f", value));
                }
            };
            listTasks.setAdapter(adapter);
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    private void updateData() {
        new CurrencyUpdateTask(getContentResolver()).execute(preferences);
        if (preferences.contains(LAST_UPDATE)) {
            TextView lastUpdate = (TextView) findViewById(R.id.last_update);
            Date date = new Date(preferences.getLong(LAST_UPDATE, 0));
            lastUpdate.setText(date.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void onCurrencyClick(View view) {
        Intent intent = new Intent(this, CalcActivity.class);
        intent.putExtra(URL_NAME, ((TextView) view.findViewById(R.id.name)).getText().toString());
        intent.putExtra(URL_CURRENCY, Double.valueOf(((TextView) view.findViewById(R.id.to_value)).getText().toString()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        updateData();
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ENTRIES_URI, null, null, null, DB_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
