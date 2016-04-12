package com.csc.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReaderOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasklist.db";

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + FeedsTable.TABLE_NAME
                    + "("
                    + FeedsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FeedsTable.COLUMN_CURRENCY + " TEXT, "
                    + FeedsTable.COLUMN_VALUE + " DOUBLE, "
                    + FeedsTable.COLUMN_DATE + " DATETIME, "
                    + FeedsTable.COLUMN_PRIORITY + " INTEGER DEFAULT 0, " //BOOLEAN
                    + FeedsTable.COLUMN_STAR + " INTEGER DEFAULT 0 " //BOOLEAN
                    + ")";

    public ReaderOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //skip now
    }
}
