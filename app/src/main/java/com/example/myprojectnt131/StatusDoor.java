package com.example.myprojectnt131;

public class StatusDoor {
    private String username;
    private boolean currentStatus;
    public StatusDoor() {
    }

    public boolean getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

