package com.example.myhobbies;

import android.net.Uri;

public class Users {
    String FirstName;
    String LastName;
    String Email;
    String Profile;



    public Users(String toString, String toString1, String email, String profile) {
        this.FirstName = toString;
        this.LastName = toString1;
        this.Email = email;
        this.Profile = profile;

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

    public void setLastName(String lastName) { LastName = lastName; }

    public String getProfile() { return Profile; }

    public void setProfile(String profile) { Profile = profile; }

    public String getEmail() { return Email; }

    public void setEmail(String email) { Email = email; }





}
