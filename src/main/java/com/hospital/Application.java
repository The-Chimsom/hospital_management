package com.hospital;

import com.hospital.config.DatabaseConfig;
import com.hospital.config.SparkConfig;
import com.hospital.controllers.AuthController;
import com.hospital.controllers.ClerkController;
import com.hospital.controllers.NurseController;
import com.hospital.services.AuthService;

public class Application {
    public static void main(String[] args) {
        // Initialize database connection (Singleton)
        DatabaseConfig.getInstance();

        // Initialize AuthService (Singleton)

        // Initialize controllers
        new SparkConfig();
        new AuthController();
        new ClerkController();
        new NurseController();

        System.out.println("Hospital Management System API started on port 4567");
    }
}