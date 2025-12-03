package com.github.satwiksanand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {

    public static String hash(String original) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = messageDigest.digest(original.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(encodedHash);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        //I am limiting the number of links it will process.

        Map<String, String> allData = new HashMap<>();//url -> HTML extracted.
        Set<String> seenUrls = new HashSet<>();//already seen urls
        Set<String> seenContentHash = new HashSet<>();//hash of the seen content.
        Queue<String> urlQueue = new ArrayDeque<>();

        urlQueue.offer("https://jsoup.org/");
        seenUrls.add("https://jsoup.org/");

        int count = 0;

        while(!urlQueue.isEmpty() && count < 10){
            count += 1;
            String currentUrl = urlQueue.poll();
            Document doc = Jsoup.connect(currentUrl).get();
            String safeContent = Jsoup.clean(doc.toString(), Safelist.basic());
            Elements allLinks = doc.getElementsByTag("a");
            for(Element link : allLinks){
                String curr = link.attr("abs:href");
                if(!seenUrls.contains(curr)){
                    urlQueue.offer(curr);
                    seenUrls.add(curr);
                }
            }
            String pageHash = hash(safeContent);
            if(!seenContentHash.contains(safeContent)){
                allData.put(currentUrl, safeContent);
                seenContentHash.add(pageHash);
            }
        }

        for(Map.Entry<String, String> entry : allData.entrySet()){
            System.out.println("link processed: " + entry.getKey());
            System.out.println("content stored: " + entry.getValue());
        }
    }
}