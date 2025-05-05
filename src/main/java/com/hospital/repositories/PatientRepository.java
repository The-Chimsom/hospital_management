
package com.hospital.repositories;

import com.hospital.config.DatabaseConfig;
import com.hospital.models.Patient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientRepository {
    private MongoCollection<Document> collection;

    public PatientRepository() {
        this.collection = DatabaseConfig.getInstance().getDatabase().getCollection("patients");
    }

    public void save(Patient patient) {
        Document doc = new Document("_id", patient.getId())
                .append("patientId", patient.getPatientId())
                .append("firstName", patient.getFirstName())
                .append("lastName", patient.getLastName())
                .append("dateOfBirth", patient.getDateOfBirth())
                .append("gender", patient.getGender())
                .append("contactNumber", patient.getContactNumber())
                .append("address", patient.getAddress())
                .append("scheduledForVitals", patient.isScheduledForVitals())
                .append("scheduledDate", patient.getScheduledDate());

        collection.insertOne(doc);
    }

    public Patient findByPatientId(String patientId) {
        Document doc = collection.find(Filters.eq("patientId", patientId)).first();
        return documentToPatient(doc);
    }

    public List<Patient> findScheduledForVitals() {
        List<Patient> patients = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("scheduledForVitals", true))) {
            patients.add(documentToPatient(doc));
        }
        return patients;
    }

    public void scheduleForVitals(String patientId, Date scheduledDate) {
        collection.updateOne(
                Filters.eq("patientId", patientId),
                Updates.combine(
                        Updates.set("scheduledForVitals", true),
                        Updates.set("scheduledDate", scheduledDate)
                )
        );
    }

    public void completeVitals(String patientId) {
        collection.updateOne(
                Filters.eq("patientId", patientId),
                Updates.combine(
                        Updates.set("scheduledForVitals", false),
                        Updates.unset("scheduledDate")
                )
        );
    }

    private Patient documentToPatient(Document doc) {
        if (doc == null) {
            return null;
        }

        Patient patient = new Patient(
                doc.getString("patientId"),
                doc.getString("firstName"),
                doc.getString("lastName"),
                doc.getDate("dateOfBirth"),
                doc.getString("gender"),
                doc.getString("contactNumber"),
                doc.getString("address")
        );

        patient.setId(doc.getObjectId("_id"));
        patient.setScheduledForVitals(doc.getBoolean("scheduledForVitals", false));
        patient.setScheduledDate(doc.getDate("scheduledDate"));

        return patient;
    }
}