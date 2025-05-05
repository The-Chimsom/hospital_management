package com.hospital.repositories;

import com.hospital.config.DatabaseConfig;
import com.hospital.models.VitalsRecord;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class VitalsRepository {
    private MongoCollection<Document> collection;

    public VitalsRepository() {
        this.collection = DatabaseConfig.getInstance().getDatabase().getCollection("vitals");
    }

    public void save(VitalsRecord vitals) {
        Document doc = new Document("_id", vitals.getId())
                .append("patientId", vitals.getPatientId())
                .append("temperature", vitals.getTemperature())
                .append("height", vitals.getHeight())
                .append("weight", vitals.getWeight())
                .append("bloodPressure", vitals.getBloodPressure())
                .append("sugarLevel", vitals.getSugarLevel())
                .append("recordedDate", vitals.getRecordedDate())
                .append("recordedBy", vitals.getRecordedBy());

        collection.insertOne(doc);
    }

    public List<VitalsRecord> findByPatientId(ObjectId patientId) {
        List<VitalsRecord> records = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("patientId", patientId))) {
            records.add(documentToVitalsRecord(doc));
        }
        return records;
    }

    private VitalsRecord documentToVitalsRecord(Document doc) {
        if (doc == null) {
            return null;
        }

        VitalsRecord record = new VitalsRecord(
                doc.getObjectId("patientId"),
                doc.getDouble("temperature").floatValue(),
                doc.getDouble("height").floatValue(),
                doc.getDouble("weight").floatValue(),
                doc.getString("bloodPressure"),
                doc.getDouble("sugarLevel").floatValue(),
                doc.getObjectId("recordedBy")
        );

        record.setId(doc.getObjectId("_id"));
        record.setRecordedDate(doc.getDate("recordedDate"));

        return record;
    }
}