package com.hospital.services;

import com.hospital.models.Patient;
import com.hospital.models.VitalsRecord;
import com.hospital.repositories.PatientRepository;
import com.hospital.repositories.VitalsRepository;
import org.bson.types.ObjectId;

import java.util.List;

public class NurseService {
    private PatientRepository patientRepository;
    private VitalsRepository vitalsRepository;

    public NurseService() {
        this.patientRepository = new PatientRepository();
        this.vitalsRepository = new VitalsRepository();
    }

    public List<Patient> getScheduledPatients() {
        return patientRepository.findScheduledForVitals();
    }

    public VitalsRecord recordVitals(String patientId, float temperature, float height, float weight,
                                     String bloodPressure, float sugarLevel, ObjectId nurseId) {
        // Find patient
        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " not found");
        }

        // Create and save vitals record
        VitalsRecord vitalsRecord = new VitalsRecord(patient.getId(), temperature, height, weight,
                bloodPressure, sugarLevel, nurseId);
        vitalsRepository.save(vitalsRecord);

        // Mark patient as completed for vitals
        patientRepository.completeVitals(patientId);

        return vitalsRecord;
    }

    public List<VitalsRecord> getPatientVitalsHistory(String patientId) {
        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " not found");
        }

        return vitalsRepository.findByPatientId(patient.getId());
    }
}