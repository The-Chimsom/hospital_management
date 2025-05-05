package com.hospital.models;

public class Clerk extends User {
    private String clerkId;
    private String desk;

    public Clerk(String username, String password, String email, String clerkId, String desk) {
        super(username, password, email, "CLERK");
        this.clerkId = clerkId;
        this.desk = desk;
    }

    // Getters and setters
    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}