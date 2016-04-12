package com.lpaina.colloquium1;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
            parser.setInput(url.openStream(), "UTF_8");

            cards = new ArrayList<>();
            Card card = null;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName().toLowerCase();
                    switch (name) {
                        case "item":
                            card = new Card();
                            break;
                        case "description":
                            if (card != null) {
                                card.setTitle(parser.nextText());
                                card.setValue("");
                            }
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                    if (card != null) {
                        cards.add(card.clone());
                        card = null;
                    }
                }

                eventType = parser.next(); //move to next element
            }

        } catch (XmlPullParserException | IOException | CloneNotSupportedException e) {
            Log.e(TAG, "doInBackground: ", e);
        }

        return cards;
    }

}
