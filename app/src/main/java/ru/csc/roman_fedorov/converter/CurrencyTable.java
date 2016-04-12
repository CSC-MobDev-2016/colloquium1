package ru.csc.roman_fedorov.converter;

import android.provider.BaseColumns;

/**
 * Created by roman on 12.04.2016.
 */
public interface CurrencyTable extends BaseColumns {
    String TABLE_NAME = "Currency";
    String CURRENCY_NAME = "Name";
    String CURRENCY_VALUE = "Value";
}
