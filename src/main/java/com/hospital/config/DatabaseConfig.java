package com.hospital.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private DatabaseConfig() {
        // Private constructor to prevent instantiation
        String connectionString = "mongodb://localhost:27017/HOSPITAL_MANAGEMENT";
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("HOSPITAL_MANAGEMENT");
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}