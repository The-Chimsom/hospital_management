package com.hospital.observers;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String patientId, String message) {
        for (Observer observer : observers) {
            observer.update(patientId, message);
        }
    }
}