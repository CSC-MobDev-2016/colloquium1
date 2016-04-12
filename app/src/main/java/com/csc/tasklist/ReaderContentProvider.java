package com.csc.tasklist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class ReaderContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.csc.tasklist";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int ENTRIES = 1;
    public static final int ENTRIES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "/entries", ENTRIES);
        uriMatcher.addURI(AUTHORITY, "/entries/#", ENTRIES_ID);
    }

    private ReaderOpenHelper helper;

    public ReaderContentProvider() {
        helper = new ReaderOpenHelper(getContext());
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        String tableName;
        switch (match) {
            case ENTRIES:
                tableName = FeedsTable.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        int count = helper.getWritableDatabase().delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        String tableName;
        switch (match) {
            case ENTRIES:
                tableName = FeedsTable.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        long rowId = helper.getWritableDatabase().insert(tableName, null, values);
        Uri inserted = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(inserted, null);
        return inserted;
    }

    @Override
    public boolean onCreate() {
        helper = new ReaderOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (match) {
            case ENTRIES:
                builder.setTables(FeedsTable.TABLE_NAME);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        String tableName;
        switch (match) {
            case ENTRIES:
                tableName = FeedsTable.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        int count = helper.getWritableDatabase().update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
