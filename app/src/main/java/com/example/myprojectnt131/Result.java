package com.example.myprojectnt131;

public class Result {
    private boolean status;
    private boolean statusDoor;
    public Result() {

    }
    private boolean getStatus() {
        return status;
    }
    private void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatusDoor() {
        return statusDoor;
    }

    public void setStatusDoor(boolean statusDoor) {
        this.statusDoor = statusDoor;
    }
}
