package com.lpaina.colloquium1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import hugo.weaving.DebugLog;

@DebugLog
public class ReaderContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.lpaina.colloquium1";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int ENTRIES = 1;
    private static final int ENTRIES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TAG = "ContentProvider";

    static {
        uriMatcher.addURI(AUTHORITY, "/entries", ENTRIES);
        uriMatcher.addURI(AUTHORITY, "/entries/#", ENTRIES_ID);
    }


    public ReaderContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        RSSParserTask task = new RSSParserTask();
        task.execute(selection);

        try {
            List<Card> cards = task.get(5, TimeUnit.SECONDS);
            for (Card card : cards) {
                cursor.addRow(new String[]{card.getTitle(), card.getValue()});
            }
        } catch (ExecutionException | InterruptedException | TimeoutException | NullPointerException e) {
            Log.e(TAG, "query: cannot receive data", e);
            return null;
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
