package com.malinovsky239.currencyconverter;

import android.provider.BaseColumns;

interface FeedsTable extends BaseColumns {
    String TABLE_NAME = "currencies";
    String COLUMN_NAME = "currency";
    String COLUMN_RATE = "rate";
    String COLUMN_DATETIME = "datetime";
    String COLUMN_ID = "_id";
}
