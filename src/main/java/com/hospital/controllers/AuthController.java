package com.hospital.controllers;

import com.google.gson.Gson;
import com.hospital.models.User;
import com.hospital.services.AuthService;
import com.hospital.utils.ResponseUtil;
import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private AuthService authService;
    private Gson gson;

    public AuthController() {
        this.authService = new AuthService();
        this.gson = new Gson();

        setupRoutes();
    }

    private void setupRoutes() {
        post("/api/auth/login", (request, response) -> {
            try {
                Map<String, String> body = gson.fromJson(request.body(), Map.class);
                String username = body.get("username");
                String password = body.get("password");

                String token = authService.login(username, password);
                Map<String, String> responseData = new HashMap<>();
                responseData.put("token", token);

                return ResponseUtil.success(responseData);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });

        post("/api/auth/register", (request, response) -> {
            try {
                Map<String, String> body = gson.fromJson(request.body(), Map.class);
                String userType = body.get("userType");
                String username = body.get("username");
                String password = body.get("password");
                String email = body.get("email");
                String id = body.get("id");
                String additionalInfo = body.get("additionalInfo");

                User user = authService.register(userType, username, password, email, id, additionalInfo);

                return ResponseUtil.success(user);
            } catch (Exception e) {
                ResponseUtil.setErrorResponse(response, 400, e.getMessage());
                return response.body();
            }
        });
    }
}