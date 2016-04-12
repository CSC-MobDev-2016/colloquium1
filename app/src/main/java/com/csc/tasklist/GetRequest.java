package com.csc.tasklist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Oleg Doronin
 * TaskList
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class GetRequest {
    private final  int TIME_OUT = 5000;
    public String sendGet() throws Exception {
        URL urlObject = new URL("http://www.cbr.ru/scripts/XML_daily.asp?");
        HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setRequestProperty("Content-Type","text/plain; charset=UTF-8");
        connection.setRequestProperty("Accept-Charset", "UTF-8");


        connection.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();

        return response.toString();
    }
}
