package com.example.myhobbies;

public class Users {
    String FirstName;
    String LastName;

    public Users() {
    }

    public Users(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;

    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
