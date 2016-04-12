package com.malinovsky239.currencyconverter;

import android.content.Context;
import android.database.MatrixCursor;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;


public class rssXMLParser extends AsyncTask<String, Void, MatrixCursor> {

    Context context;

    public rssXMLParser(Context context) {
        this.context = context;
    }

    private String extractDouble(String s) {
        s = s.substring(s.indexOf('=') + 2);
        return s.substring(0, s.indexOf(' '));
    }

    @Override
    protected MatrixCursor doInBackground(String... params) {
        MatrixCursor result = null;

        try {
            int eventType;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            result = new MatrixCursor(new String[]{"title", "pubDate", "rate"}, 0);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(params[0]));
            eventType = xpp.getEventType();
            String title = "", pubDate = "", rate = "";
            boolean titleSet = false, pubDateSet = false, rateSet = false, isItem = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = xpp.getName();
                    if (tagName.equals("item")) {
                        isItem = true;
                    }
                    if (isItem && (tagName.equals("title") || tagName.equals("pubDate") || tagName.equals("description"))) {
                        try {
                            try {
                                xpp.next();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String text = xpp.getText();
                        if (tagName.equals("title")) {
                            title = text.substring(text.length() - 4, text.length() - 1);
                            titleSet = true;
                        }
                        if (tagName.equals("pubDate")) {
                            pubDate = text;
                            pubDateSet = true;
                        }
                        if (tagName.equals("description")) {
                            rate = extractDouble(text);
                            rateSet = true;
                        }
                        if (titleSet && pubDateSet && rateSet) {
                            result.addRow(new String[]{title, pubDate, rate});
                            /*System.out.println(title);
                            System.out.println(pubDate);
                            System.out.println(rate);*/
                            titleSet = false;
                            pubDateSet = false;
                            rateSet = false;
                        }
                    }
                }
                eventType = xpp.next();
            }
            return result;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(MatrixCursor result) {
        ((MainActivity) context).addToContentProvider(result);
    }

}