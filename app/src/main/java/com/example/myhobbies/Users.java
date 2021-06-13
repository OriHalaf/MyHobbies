package com.example.myhobbies;

import android.media.Image;
import android.widget.ImageView;

public class Users {
    String FirstName;
    String LastName;
    ImageView Profile;

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

    public ImageView getProfile() {
        return Profile;
    }

    public void setProfile(ImageView profile) {
        Profile = profile;
    }
}
