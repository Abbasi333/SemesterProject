package com.example.semesterproject;

import java.io.Serializable;

public class Donor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String bloodType;
    private String address;
    private String phoneNumber;
    private String imageUrl;

    public Donor(){}

    public Donor(String name, String email, String bloodType, String address, String phoneNumber, String imageUrl) {
        this.name = name;
        this.email = email;
        this.bloodType = bloodType;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    // Getters and setters for the fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return imageUrl;
    }

    public void setProfilePictureUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}