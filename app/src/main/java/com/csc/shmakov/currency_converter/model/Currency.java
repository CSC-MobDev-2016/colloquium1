package com.csc.shmakov.currency_converter.model;

/**
 * Created by Pavel on 4/3/2016.
 */
public class Currency {
    long id;
    public final String name;
    public final float value;

    public Currency(String name, float value) {
        this.name = name;
        this.value = value;
    }
}
