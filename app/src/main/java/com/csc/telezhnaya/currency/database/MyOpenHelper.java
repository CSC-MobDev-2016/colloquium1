package com.csc.telezhnaya.currency.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "com.csc.telezhnaya.currency";

    private static final String SQL_CREATE_ENTRIES_TABLE = "CREATE TABLE " + CurrencyTable.TABLE_NAME + "("
            + CurrencyTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CurrencyTable.COLUMN_CURRENCY_NAME + " TEXT, "
            + CurrencyTable.COLUMN_RATE + " DOUBLE)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CurrencyTable.TABLE_NAME;

    public MyOpenHelper(Context context) {
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
