package com.github.satwiksanand;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    // fetch the seed url at first.
    public static void main(String[] args) {
        // System.out.print("Please enter the seed url:-> ");
        // String seedUrl = scanner.next();
        // System.out.println(seedUrl);
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("sample_mflix");
            try {
                mongoDatabase.createCollection("kachra");
                System.out.println("Collection 'kachra' created successfully.");
            } catch (Exception e) {
                System.out.println("Collection 'kachra' already exists or could not be created: " + e.getMessage());
            }
        }
    }
}