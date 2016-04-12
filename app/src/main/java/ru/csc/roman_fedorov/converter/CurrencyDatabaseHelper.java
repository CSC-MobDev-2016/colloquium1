package ru.csc.roman_fedorov.converter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roman on 12.04.2016.
 */
public class CurrencyDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "todoDatabase";

    private static CurrencyDatabaseHelper sInstance;

    public static synchronized CurrencyDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CurrencyDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private CurrencyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_CURRENCY_TABLE =
            "CREATE TABLE " + CurrencyTable.TABLE_NAME
                    + "("
                    + CurrencyTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CurrencyTable.CURRENCY_NAME + " TEXT, "
                    + CurrencyTable.CURRENCY_VALUE + " TEXT"
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CurrencyTable.TABLE_NAME;

    public boolean dataExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean exists = false;
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery("SELECT * FROM " + CurrencyTable.TABLE_NAME, null);
            exists = mCursor.moveToFirst();
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return exists;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CURRENCY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
