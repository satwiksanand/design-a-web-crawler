package com.github.satwiksanand;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LinkExtractor {
    public static void extractLinks(Document doc){
        Elements allLinks = doc.select("a");
        List<String> extractedLinks = new ArrayList<>();
        for(Element curr : allLinks){
            extractedLinks.add(curr.attr("abs:href"));
        }
        //pass it to link filter
    }
}
