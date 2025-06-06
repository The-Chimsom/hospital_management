package com.hospital;

import com.hospital.controllers.AuthController;
import com.hospital.controllers.ClerkController;
import com.hospital.controllers.NurseController;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig.getInstance();


        new SparkConfig();
        new AuthController();
        new ClerkController();
        new NurseController();

        System.out.println("Hospital Management System API started on port 4567");
    }
}