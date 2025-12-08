package com.github.satwiksanand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class ContentParser {
    public static String parseContent(Document doc){
        return Jsoup.clean(doc.toString(), Safelist.basic());
    }
}
