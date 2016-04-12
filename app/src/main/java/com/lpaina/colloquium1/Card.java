package com.lpaina.colloquium1;

public class Card implements Cloneable {
    public static final String TITLE_NAME = "TITLE";
    public static final String VALUE_NAME = "VALUE";
    public static final String[] NAMES = new String[]{TITLE_NAME, VALUE_NAME};
    private String title;
    private String value;

    public Card() {

    }

    public Card(String title, String value) {
        this.title = title;
        this.value = value;
    }

    @Override
    protected Card clone() throws CloneNotSupportedException {
        super.clone();
        return new Card(title, value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
