package com.hospital.observers;

public interface Observer {
    void update(String patientId, String message);
}