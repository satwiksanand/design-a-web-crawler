package com.github.satwiksanand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        System.out.println("Please enter the seed url");
//        String seedUrl = scanner.next();
//        System.out.println(seedUrl);

        String[] urls = {
                "https://www.javaspring.net/blog/convert-list-of-objects-to-array-java/",
                "http://subdomain.example.org:8080/api",
                "https://github.com/user/repo?query=value",
                "www.test.com/page",
                "ftp://files.server.com:21/download"
        };

        List<String> allLines = new ArrayList<>();
        System.out.println(new File("src\\main\\java\\com\\github\\satwiksanand\\robotExample.txt").getCanonicalPath());
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\com\\github\\satwiksanand\\robotExample.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                allLines.add(line);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        RobotParser rule = RobotParser.parse(allLines, "satwikBot");
    }
}