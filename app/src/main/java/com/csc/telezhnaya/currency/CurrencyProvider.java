package com.csc.telezhnaya.currency;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyProvider extends AsyncTask<String, Void, ArrayList<Item>> {

    private AppCompatActivity activity;
    private Context context;

    CurrencyProvider(AppCompatActivity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    protected ArrayList<Item> doInBackground(String... params) {
        ArrayList<Item> result = new ArrayList<>();
        try {
            URL url = new URL("http://api.fixer.io/latest?base=RUB");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();



            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(connection.getInputStream(), null);
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("rates")) {
                    while (eventType != XmlPullParser.END_TAG) {
                        Item item = new Item();
                        item.name = xpp.nextText();
                        item.from = Double.parseDouble(xpp.nextText());
                        item.to = 1. / Double.parseDouble(xpp.nextText());
                        result.add(item);
                        eventType = xpp.getEventType();
                    }
                    break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Item>  result) {
        super.onPostExecute(result);
        ListView rssListView = (ListView) activity.findViewById(R.id.list);
        rssListView.setAdapter(new ArrayAdapter<>(context, R.layout.currency, result));
    }

}
