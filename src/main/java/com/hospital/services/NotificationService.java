package com.hospital.services;

public class NotificationService {

    public void sendNotification(String title, String message) {
        // In a real application, this would send emails, SMS, or push notifications
        // For demonstration purposes, we'll just log to console
        System.out.println("NOTIFICATION: " + title);
        System.out.println("MESSAGE: " + message);
    }
}
