package com.lpaina.colloquium1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
    private static final String PATH_TO_COURSE = "http://www.cbr.ru/scripts/RssCurrency.asp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = getContentResolver().query(ReaderContentProvider.CONTENT_URI,
                Card.NAMES, PATH_TO_COURSE, null, null);

        RVAdapter adapter = new RVAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }
}
