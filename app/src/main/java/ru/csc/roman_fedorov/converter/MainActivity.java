package ru.csc.roman_fedorov.converter;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(CurrencyContentProvider.CONTENT_URI, "entries");

    private MyCursorAdapter adapter;
    private String[] ratesOnServer = new String[]{"AUD", "CAD", "CNY", "JPY", "NZD", "USD", "EUR", "BGN"};
    public static final String API_URL = "http://api.fixer.io/latest?base=RUB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadCurrencyAsyncTask().execute();

        getSupportLoaderManager().initLoader(0, null, this);

        ListView ratesListView = (ListView) findViewById(R.id.rates_list_view);
        adapter = new MyCursorAdapter(this, null, 0);
        ratesListView.setAdapter(adapter);

        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadCurrencyAsyncTask().execute();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new DownloadCurrencyAsyncTask().execute();
        }
        return true;
    }

    ;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, ENTRIES_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    class DownloadCurrencyAsyncTask extends AsyncTask<Object, Void, double[]> {
        @Override
        protected double[] doInBackground(Object... objects) {
            String url = API_URL;
            double[] data = null;
            try {
                data = loadFromNetwork(url);
            } catch (Exception e) {
                return null;
            }
            return data;
        }

        protected void onPostExecute(double[] data) {
            if (data == null) {
                Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            } else {
                boolean dataAlreadyExists = CurrencyDatabaseHelper.getInstance(MainActivity.this).dataExists();
                for (int i = 0; i < ratesOnServer.length; i++) {
                    ContentValues newValues = new ContentValues();
                    newValues.put(CurrencyTable.CURRENCY_NAME, ratesOnServer[i]);
                    newValues.put(CurrencyTable.CURRENCY_VALUE, String.valueOf(data[i]));
                    String mSelectionClause = CurrencyTable.CURRENCY_NAME + " LIKE ?";
                    if (dataAlreadyExists) {
                        String[] mSelectionArgs = {ratesOnServer[i]};
                        getContentResolver().update(MainActivity.ENTRIES_URI, newValues, mSelectionClause, mSelectionArgs);
                    } else {
                        getContentResolver().insert(MainActivity.ENTRIES_URI, newValues);
                    }
                }
            }
        }

        private double[] loadFromNetwork(String urlString) {
            InputStream stream = null;
            double[] currency = null;
            try {
                Scanner s = new java.util.Scanner(downloadUrl(urlString)).useDelimiter("\\A");
                String json = s.hasNext() ? s.next() : "";
                JSONObject serverOutput = new JSONObject(json);
                JSONObject rates = serverOutput.getJSONObject("rates");
                currency = new double[ratesOnServer.length];
                for (int i = 0; i < ratesOnServer.length; i++) {
                    currency[i] = 1.0 / rates.getDouble(ratesOnServer[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return currency;
        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000 /* milliseconds */);
            return conn.getInputStream();
        }
    }
}
