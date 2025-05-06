package com.hospital.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hospital.models.Patient;
import com.hospital.models.User;
import com.hospital.observers.VitalsScheduleObserver;
import com.hospital.services.AuthService;
import com.hospital.services.ClerkService;
import com.hospital.utils.ResponseUtil;
import static spark.Spark.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClerkController {
    private ClerkService clerkService;
    private AuthService authService;
    private Gson gson;

    public ClerkController() {
        this.clerkService = new ClerkService();
        this.authService = new AuthService();

        // Add observer for vitals scheduling
        this.clerkService.attach(new VitalsScheduleObserver());

        // Configure Gson to handle dates properly
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        setupRoutes();
    }

    private void setupRoutes() {
        // Middleware to check for clerk authorization
        before("/api/clerk/*", (request, response) -> {
            String authHeader = request.headers("Authorization");
            if (authHeader == null) {
                halt(401, ResponseUtil.error("Authorization header required"));
            }

            try {
                User user = authService.validateToken(authHeader);
                if (!"CLERK".equals(user.getRole())) {
                    halt(403, ResponseUtil.error("Access denied"));
                }
                request.attribute("user", user);
            } catch (Exception e) {
                halt(401, ResponseUtil.error(e.getMessage()));
            }
        });

        // Register a new patient
        post("/api/clerk/register-patient", (request, response) -> {
            try {
                Map<String, Object> body = gson.fromJson(request.body(), Map.class);

                String patientId = (String) body.get("patientId");
                String firstName = (String) body.get("firstName");
                String lastName = (String) body.get("lastName");
                Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse((String) body.get("dateOfBirth"));
                String gender = (String) body.get("gender");
                String contactNumber = (String) body.get("contactNumber");
                String address = (String) body.get("address");

                Patient patient = clerkService.registerPatient(
                        patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address
                );

                return ResponseUtil.success(patient);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });

        // Schedule a patient for vitals
        post("/api/clerk/schedule-vitals", (request, response) -> {
            try {
                Map<String, Object> body = gson.fromJson(request.body(), Map.class);

                String patientId = (String) body.get("patientId");
                Date scheduledDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) body.get("scheduledDate"));

                Patient patient = clerkService.scheduleVitals(patientId, scheduledDate);

                return ResponseUtil.success(patient);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });
    }
}