package com.github.satwiksanand;

import com.mongodb.client.*;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        System.out.println("Please enter the seed url");
//        String seedUrl = scanner.next();
//        System.out.println(seedUrl);

        String[] urls = {
                "https://www.example.com/path/to/page",
                "http://subdomain.example.org:8080/api",
                "https://github.com/user/repo?query=value",
                "www.test.com/page",
                "ftp://files.server.com:21/download"
        };
        URLFrontierImpl.insertUrls(urls);
        System.out.println(URLFrontierImpl.fetchNextURL());
    }
}