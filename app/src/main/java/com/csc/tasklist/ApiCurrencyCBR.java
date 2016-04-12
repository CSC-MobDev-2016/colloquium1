package com.csc.tasklist;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/*
import org.w3c.dom.*;
import javax.xml.parsers.*;
*/

/**
 * Created by Oleg Doronin
 * TaskList
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class ApiCurrencyCBR {
    public List<CurrencyItem> getCurrency()
    {
        GetRequest r = new GetRequest();
        String message;
        try {
            ArrayList<CurrencyItem> items = new ArrayList<CurrencyItem>();
            message = r.sendGet();

            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(message));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            String date_ = doc.getDocumentElement().getAttribute("Date");
            NodeList nList = doc.getElementsByTagName("Valute");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                CurrencyItem citem = new CurrencyItem();
                citem.date = date_;
                citem.date = ((Element) nNode).getElementsByTagName("NumCode").item(0).getNodeValue();
                citem.value = ((Element) nNode).getElementsByTagName("Value").item(0).getNodeValue();
                citem.charCode = ((Element) nNode).getElementsByTagName("CharCode").item(0).getNodeValue();
                items.add(citem);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
