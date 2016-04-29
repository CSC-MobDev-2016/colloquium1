package com.csc.telezhnaya.currency.database;

import android.provider.BaseColumns;

public interface CurrencyTable extends BaseColumns {
    String TABLE_NAME = "CURRENCY";

    String COLUMN_CURRENCY_NAME = "CURRENCY_NAME";
    String COLUMN_RATE = "RATE";
}
