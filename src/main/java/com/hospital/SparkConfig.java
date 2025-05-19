package com.hospital;

import static spark.Spark.*;

public class SparkConfig {

    public SparkConfig() {
        // Configure Spark
        port(4567);

        // Enable CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
            response.type("application/json");
        });

        // Handle 404s
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"error\": \"Resource not found\"}";
        });

        // Handle exceptions
        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.type("application/json");
            res.body("{\"error\": \"" + e.getMessage() + "\"}");
        });
    }
}