
package com.hospital.utils;

import com.google.gson.Gson;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    private static final Gson gson = new Gson();

    public static String success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return gson.toJson(response);
    }

    public static String error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return gson.toJson(response);
    }

    public static void setErrorResponse(Response response, int status, String message) {
        response.status(status);
        response.body(error(message));
    }
}
