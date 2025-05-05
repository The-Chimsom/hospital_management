package com.hospital.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hospital.models.Patient;
import com.hospital.models.User;
import com.hospital.models.VitalsRecord;
import com.hospital.services.AuthService;
import com.hospital.services.NurseService;
import com.hospital.utils.ResponseUtil;
import org.bson.types.ObjectId;
import static spark.Spark.*;

import java.util.List;
import java.util.Map;

public class NurseController {
    private NurseService nurseService;
    private AuthService authService;
    private Gson gson;

    public NurseController() {
        this.nurseService = new NurseService();
        this.authService = new AuthService();

        // Configure Gson to handle dates properly
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm")
                .create();

        setupRoutes();
    }

    private void setupRoutes() {
        // Middleware to check for nurse authorization
        before("/api/nurse/*", (request, response) -> {
            String authHeader = request.headers("Authorization");
            if (authHeader == null) {
                halt(401, ResponseUtil.error("Authorization header required"));
            }

            try {
                User user = authService.validateToken(authHeader);
                if (!"NURSE".equals(user.getRole())) {
                    halt(403, ResponseUtil.error("Access denied"));
                }
                request.attribute("user", user);
            } catch (Exception e) {
                halt(401, ResponseUtil.error(e.getMessage()));
            }
        });

        // Get patients scheduled for vitals
        get("/api/nurse/scheduled-patients", (request, response) -> {
            try {
                List<Patient> patients = nurseService.getScheduledPatients();
                return ResponseUtil.success(patients);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });

        // Record vitals for a patient
        post("/api/nurse/record-vitals", (request, response) -> {
            try {
                User nurse = request.attribute("user");
                Map<String, Object> body = gson.fromJson(request.body(), Map.class);

                String patientId = (String) body.get("patientId");
                float temperature = ((Double) body.get("temperature")).floatValue();
                float height = ((Double) body.get("height")).floatValue();
                float weight = ((Double) body.get("weight")).floatValue();
                String bloodPressure = (String) body.get("bloodPressure");
                float sugarLevel = ((Double) body.get("sugarLevel")).floatValue();

                VitalsRecord vitals = nurseService.recordVitals(
                        patientId, temperature, height, weight, bloodPressure, sugarLevel, nurse.getId()
                );

                return ResponseUtil.success(vitals);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });
    }
}