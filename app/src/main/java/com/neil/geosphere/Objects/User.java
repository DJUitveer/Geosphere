package com.neil.geosphere.Objects;

public class User {
    //object variables
    private String name;
    private String surname;
    private boolean unitOfMeasurement;//metric=true imperial=false
    private String email;
    private String password;

    //parameterized constructor
    public User(String name, String surname, boolean unitOfMeasurement, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.unitOfMeasurement = unitOfMeasurement;
        this.email = email;
        this.password = password;
    }

    //empty constructor
    public User() {
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public boolean isUnitOfMeasurement() {
        return unitOfMeasurement;
    }
    public void setUnitOfMeasurement(boolean metric) {
        this.unitOfMeasurement = metric;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //password hashing method
    public void hashPassword(String password) {
        //Todo: hash password
    }
}
