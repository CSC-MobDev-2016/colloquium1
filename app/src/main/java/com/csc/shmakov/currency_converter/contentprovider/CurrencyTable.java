package com.csc.shmakov.currency_converter.contentprovider;

import android.provider.BaseColumns;

/**
 * Created by Pavel on 4/3/2016.
 */
public interface CurrencyTable extends BaseColumns {
    String TABLE_NAME = "currency";

    String COLUMN_NAME = "name";
    String COLUMN_VALUE = "value";
}
