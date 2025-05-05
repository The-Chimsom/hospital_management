package com.hospital.observers;

import com.hospital.services.NotificationService;

public class VitalsScheduleObserver implements Observer {
    private NotificationService notificationService;

    public VitalsScheduleObserver() {
        this.notificationService = new NotificationService();
    }

    @Override
    public void update(String patientId, String message) {
        notificationService.sendNotification("New patient scheduled for vitals: " + patientId, message);
        System.out.println("Notification sent for patient: " + patientId);
    }
}