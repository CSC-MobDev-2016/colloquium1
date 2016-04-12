package com.csc.shmakov.currency_converter.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class CurrencyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.csc.shmakov.currency";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(CurrencyContentProvider.CONTENT_URI, "entries");

    public static final int ENTRIES = 1;
    public static final int ENTRY = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "/entries", ENTRIES);
        uriMatcher.addURI(AUTHORITY, "/entries/#", ENTRY);
    }

    private DatabaseOpenHelper helper;

    public CurrencyContentProvider() {
        helper = new DatabaseOpenHelper(getContext());
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) != ENTRIES) {
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
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != ENTRIES) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        long rowId = helper.getWritableDatabase().insert(CurrencyTable.TABLE_NAME, null, values);
        Uri insertedUri = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(insertedUri, null);
        return insertedUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (uriMatcher.match(uri) != ENTRY) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        String id = uri.getLastPathSegment();
        SQLiteDatabase db = helper.getWritableDatabase();
        int affectedRows = db.update(CurrencyTable.TABLE_NAME, values, CurrencyTable._ID + "=?", new String[]{id});
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }
}
