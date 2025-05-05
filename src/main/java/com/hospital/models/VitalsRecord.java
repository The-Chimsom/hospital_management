package com.hospital.models;

import org.bson.types.ObjectId;
import java.util.Date;

public class VitalsRecord {
    private ObjectId id;
    private ObjectId patientId;
    private float temperature;
    private float height;
    private float weight;
    private String bloodPressure;
    private float sugarLevel;
    private Date recordedDate;
    private ObjectId recordedBy;

    public VitalsRecord(ObjectId patientId, float temperature, float height, float weight,
                        String bloodPressure, float sugarLevel, ObjectId recordedBy) {
        this.id = new ObjectId();
        this.patientId = patientId;
        this.temperature = temperature;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.sugarLevel = sugarLevel;
        this.recordedDate = new Date();
        this.recordedBy = recordedBy;
    }

    // Getters and setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getPatientId() {
        return patientId;
    }

    public void setPatientId(ObjectId patientId) {
        this.patientId = patientId;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public float getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(float sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public ObjectId getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(ObjectId recordedBy) {
        this.recordedBy = recordedBy;
    }
}