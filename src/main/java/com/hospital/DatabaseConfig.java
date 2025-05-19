package com.hospital;

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

    private static class DatabaseConfigHolder{
        public static final DatabaseConfig instance = new DatabaseConfig();
    }

    public static DatabaseConfig getInstance() {
        return DatabaseConfigHolder.instance;
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