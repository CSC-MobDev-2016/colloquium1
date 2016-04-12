package com.csc.currencyconverter;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.csc.colloquium1.R;
import com.csc.currencyconverter.FeedsTable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Button btnAddTask;
    Cursor cursor;
    TaskAdapter taskAdapter;
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        btnAddTask = (Button) findViewById(R.id.add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.header_alert));
                final LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.task_alert, null);
                builder.setView(ll);


                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        String header = ((EditText) ll.findViewById(R.id.alert_header)).getText().toString();
                        String body = ((EditText) ll.findViewById(R.id.alert_body)).getText().toString();
                        values.put(FeedsTable.COLUMN_HEADER, header);
                        values.put(FeedsTable.COLUMN_BODY, body);
                        values.put(FeedsTable.COLUMN_COLOR, Color.WHITE);
                        getContentResolver().insert(ENTRIES_URI, values);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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
        return new CursorLoader(this, ENTRIES_URI, null, null, null,  FeedsTable.COLUMN_DONE + ", " + FeedsTable.COLUMN_STAR + " DESC, " + FeedsTable.COLUMN_DATE + " DESC");
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
