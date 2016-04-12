package com.csc.tasklist;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Button btnAddTask;
    Cursor cursor;
    TaskAdapter taskAdapter;
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries");


    class DataGetter extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ApiCurrencyCBR cbr = new ApiCurrencyCBR();
            List<CurrencyItem> items = cbr.getCurrency();
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    CurrencyItem item = items.get(i);
                    ContentValues values = new ContentValues();
                    values.put(FeedsTable.COLUMN_DATE, item.date);
                    values.put(FeedsTable.COLUMN_CURRENCY, item.charCode);
                    values.put(FeedsTable.COLUMN_VALUE, item.value);
                    Cursor cursor = getContentResolver().query(ENTRIES_URI, null, null, null, null);
                    getContentResolver().insert(ENTRIES_URI, values);

                }
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d("OK", "onCreate");
        ((Button)findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataGetter getter = new DataGetter();
                try {
                    getter.execute();
                } catch (Exception ex) {

                }
            }
        });


        ListView listTasks = (ListView) findViewById(R.id.list_tasks);
        taskAdapter = new TaskAdapter(this, null, this);
        listTasks.setAdapter(taskAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ENTRIES_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        taskAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        taskAdapter.swapCursor(null);
    }
}
