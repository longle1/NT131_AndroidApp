package com.example.myprojectnt131;

public class LoginRequest {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private String password;
        private String passwordDoor;
        private boolean statusDoor;
        private String username;

        public String getPassword() {
            return password;
        }

        public String getPasswordDoor() {
            return passwordDoor;
        }

        public boolean isStatusDoor() {
            return statusDoor;
        }

        public String getUsername() {
            return username;
        }
    }
}
