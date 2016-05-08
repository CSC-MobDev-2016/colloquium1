package com.csc.telezhnaya.currency;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.csc.telezhnaya.currency.database.CurrencyTable;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CurrencyUpdateTask extends AsyncTask<SharedPreferences, Void, HashMap<String, Double>> {
    private ContentResolver resolver;

    CurrencyUpdateTask(ContentResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected HashMap<String, Double> doInBackground(SharedPreferences... preferences) {
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL("http://api.fixer.io/latest?base=RUB");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String next = reader.readLine();
            do {
                json.append(next);
                next = reader.readLine();
            } while (next != null);

            reader.close();

            SharedPreferences.Editor editor = preferences[0].edit();
            editor.putLong(MainActivity.LAST_UPDATE, Calendar.getInstance().getTimeInMillis());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }

        JsonCurrencyDescription description = new Gson().fromJson(json.toString(), JsonCurrencyDescription.class);
        return description.rates;
    }

    @Override
    protected void onPostExecute(HashMap<String, Double> result) {
        for (Map.Entry<String, Double> rate : result.entrySet()) {
            ContentValues values = new ContentValues();
            values.put(CurrencyTable.COLUMN_CURRENCY_NAME, rate.getKey());
            values.put(CurrencyTable.COLUMN_RATE, rate.getValue());
            resolver.update(MainActivity.ENTRIES_URI, values,
                    CurrencyTable.COLUMN_CURRENCY_NAME + " = '" + rate.getKey() + "'", null);
        }
    }

    class JsonCurrencyDescription {
        String base;
        HashMap<String, Double> rates;
    }
}
