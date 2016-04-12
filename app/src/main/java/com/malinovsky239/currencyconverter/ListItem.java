package com.malinovsky239.currencyconverter;

public class ListItem {
    private String title;
    private String pubDate;
    private String rate;

    ListItem(String title, String pubDate, String rate) {
        this.title = title;
        this.pubDate = pubDate;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getRate() {
        return rate;
    }
}