package com.csc.telezhnaya.currency.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.csc.telezhnaya.currency";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int ENTRY_LIST = 1;
    public static final int ENTRY = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "/entries", ENTRY_LIST);
        uriMatcher.addURI(AUTHORITY, "/entries/#", ENTRY);
    }

    private MyOpenHelper helper;

    public MyContentProvider() {
        helper = new MyOpenHelper(getContext());
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != ENTRY_LIST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        int res = helper.getWritableDatabase().delete(CurrencyTable.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return res;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != ENTRY_LIST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        long id = helper.getWritableDatabase().insert(CurrencyTable.TABLE_NAME, null, values);
        Uri inserted = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(inserted, null);
        return inserted;
    }

    @Override
    public boolean onCreate() {
        helper = new MyOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) != ENTRY_LIST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CurrencyTable.TABLE_NAME);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (uriMatcher.match(uri) != ENTRY_LIST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        int res = helper.getWritableDatabase().update(CurrencyTable.TABLE_NAME, values, selection, selectionArgs);
        if (res == 0) {
            insert(uri, values);
            res = 1;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return res;
    }
}