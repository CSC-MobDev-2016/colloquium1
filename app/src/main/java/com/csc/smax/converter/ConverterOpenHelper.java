package com.csc.smax.converter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConverterOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "converter.db";

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + ValuteTable.TABLE_NAME
                    + "("
                    + ValuteTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ValuteTable.COLUMN_CODE + " TEXT, "
                    + ValuteTable.COLUMN_NAME + " TEXT, "
                    + ValuteTable.COLUMN_VALUE + " TEXT, "
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ValuteTable.TABLE_NAME;

    public ConverterOpenHelper(Context context) {
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
