package com.csc.tasklist;

import android.provider.BaseColumns;

interface FeedsTable extends BaseColumns {
    String TABLE_NAME = "TTASKLIST";

    String COLUMN_CURRENCY = "CCURRENCY";
    String COLUMN_VALUE = "CVALUE";
    String COLUMN_DATE = "CDATE";
    String COLUMN_PRIORITY = "CPRIORITY";
    String COLUMN_STAR = "STAR";
}
