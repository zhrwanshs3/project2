package com.example.project2.userfile1;

public class User {
    private String phone ;
    private String name;
    private String location ;
    private String lastname;

    public User(String name, String lastname, String location, String phone) {

        this.name = name;
        this.lastname = lastname;
        this.location = location;
        this.phone = phone;
    }

    public User() {

    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String id) {
        this.phone = phone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLastname() {
        return lastname;
    }
    public void setSpecialty(String specialty) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "phone='" + phone+ '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}



