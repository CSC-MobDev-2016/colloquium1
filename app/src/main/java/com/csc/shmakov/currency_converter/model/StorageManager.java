package com.csc.shmakov.currency_converter.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.csc.shmakov.currency_converter.contentprovider.CurrencyTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.csc.shmakov.currency_converter.contentprovider.CurrencyContentProvider.ENTRIES_URI;
import static com.csc.shmakov.currency_converter.contentprovider.CurrencyTable.COLUMN_NAME;
import static com.csc.shmakov.currency_converter.contentprovider.CurrencyTable.COLUMN_VALUE;

/**
 * Created by Pavel on 4/3/2016.
 */
public class StorageManager implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int LOADER_ID = 1;

    private final Activity activity;
    private final LoadCallback loadCallback;

    StorageManager(Activity activity, LoadCallback loadCallback) {
        this.activity = activity;
        this.loadCallback = loadCallback;
    }

    void writeItem(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, currency.name);
        values.put(COLUMN_VALUE, currency.value);
        Uri uri = activity.getContentResolver().insert(ENTRIES_URI, values);
        currency.id = Long.valueOf(uri.getLastPathSegment());
    }

    void load() {
        activity.getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private static String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    private int getInt(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    private long getLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }

    private float getFloat(Cursor cursor, String column) {
        return cursor.getFloat(cursor.getColumnIndex(column));
    }

    public void updateItem(Currency item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.name);
        values.put(COLUMN_VALUE, item.value);
        activity.getContentResolver().update(ContentUris.withAppendedId(ENTRIES_URI, item.id), values, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return new CursorLoader(activity, ENTRIES_URI, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Currency> items = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            items.add(readItem(cursor));
        }

        // This Loader business doesn't allow for precise notifications to RecyclerView
        // So we use it only for initial loading on activity creation, and don't need further notifications
        activity.getLoaderManager().destroyLoader(LOADER_ID);

        loadCallback.onItemsLoaded(items);
    }

    private Currency readItem(Cursor cursor) {
        Currency item = new Currency(
                getString(cursor, COLUMN_NAME),
                getFloat(cursor, COLUMN_VALUE));
        item.id = getLong(cursor, CurrencyTable._ID);
        return item;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loadCallback.onItemsLoaded(Collections.<Currency>emptyList());
    }

    public void write(List<Currency> data) {
        for (Currency currency : data) {
            writeItem(currency);
        }
    }

    interface LoadCallback {
        void onItemsLoaded(List<Currency> items);
    }
}
