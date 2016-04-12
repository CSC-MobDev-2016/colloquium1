package com.csc.shmakov.currency_converter.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "converter.db";

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + CurrencyTable.TABLE_NAME
                    + "("
                    + CurrencyTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CurrencyTable.COLUMN_NAME + " TEXT, "
                    + CurrencyTable.COLUMN_VALUE + " REAL"
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CurrencyTable.TABLE_NAME;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
