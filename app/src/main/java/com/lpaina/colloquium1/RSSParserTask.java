package com.lpaina.colloquium1;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hugo.weaving.DebugLog;

class RSSParserTask extends AsyncTask<String, Void, List<Card>> {

    private static final String TAG = "RSSParserTask";

    @Override
    @DebugLog
    protected List<Card> doInBackground(String... params) {
        String query = params[0];
        ArrayList<Card> cards = null;
        try {
            URL url = new URL(query);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "Windows-1251");

            cards = new ArrayList<>();
            boolean started = false;
            String info = null;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName().toLowerCase();
                    switch (name) {
                        case "item":
                            started = true;
                            break;
                        case "description":
                            if (started) {
                                info = parser.nextText();
                            }
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                    started = false;
                    String[] strings = info.split("<br>");
                    for (String string : strings) {
                        String[] values = string.split("-");
                        cards.add(new Card(values[0].trim(), values[1].trim()));
                    }
                }

                eventType = parser.next(); //move to next element
            }

        } catch (XmlPullParserException | IOException e) {
            Log.e(TAG, "doInBackground: ", e);
        }

        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card lhs, Card rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
        return cards;
    }

}
