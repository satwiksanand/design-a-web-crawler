package com.github.satwiksanand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//this class is responsible for downloading the html content and for that i am going to use jsoup
public class HTMLDownloader {
    //downloads page content from the web
    public static Document downloadPage(String url) {
        try{
            return Jsoup.connect(url).get();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
