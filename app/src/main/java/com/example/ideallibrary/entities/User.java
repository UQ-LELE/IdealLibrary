package com.example.ideallibrary.entities;

public class User {

    private String firstName;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String firstName, String password) {
        this.firstName = firstName;
        this.password = password;
    }
}
