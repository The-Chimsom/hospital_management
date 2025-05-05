package com.hospital.services;

import com.hospital.models.Patient;
import com.hospital.repositories.PatientRepository;
import com.hospital.observers.Subject;

import java.util.Date;
import java.util.List;

public class ClerkService extends Subject {
    private PatientRepository patientRepository;

    public ClerkService() {
        this.patientRepository = new PatientRepository();
    }

    public Patient registerPatient(String patientId, String firstName, String lastName, Date dateOfBirth,
                                   String gender, String contactNumber, String address) {
        // Check if patient already exists
        Patient existingPatient = patientRepository.findByPatientId(patientId);
        if (existingPatient != null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " already exists");
        }

        // Create and save new patient
        Patient newPatient = new Patient(patientId, firstName, lastName, dateOfBirth, gender, contactNumber, address);
        patientRepository.save(newPatient);

        return newPatient;
    }

    public Patient scheduleVitals(String patientId, Date scheduledDate) {
        // Find patient
        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " not found");
        }

        // Schedule patient for vitals
        patientRepository.scheduleForVitals(patientId, scheduledDate);

        // Notify observers (nurses)
        notifyObservers(patientId, "Patient " + patient.getFirstName() + " " + patient.getLastName() +
                " scheduled for vitals on " + scheduledDate);

        // Return updated patient
        return patientRepository.findByPatientId(patientId);
    }
}