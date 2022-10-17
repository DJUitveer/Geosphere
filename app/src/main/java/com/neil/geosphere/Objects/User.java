package com.neil.geosphere.Objects;

public class User {

    //object variables
    private String ID;
    private String name;
    private String surname;
    private String username;
    private String email;

    public User(String name, String surname, String username, String email ,String id) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.ID = id;
    }

    //empty constructor
    public User() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getId() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    }

