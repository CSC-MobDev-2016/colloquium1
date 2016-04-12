package com.csc.smax.converter;

import android.provider.BaseColumns;

/**
 * Created by Maxim on 12.04.2016.
 */
interface ValuteTable extends BaseColumns {
    String TABLE_NAME = "valute";

    String COLUMN_CODE = "code";
    String COLUMN_NAME = "name";
    String COLUMN_VALUE = "date";
}
