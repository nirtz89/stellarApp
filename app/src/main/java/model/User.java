package model;

import java.util.ArrayList;

public class User {
    int userId;
    String email;
    String fName;
    String lName;
    String bio;
    String dob;

    public User(int userId, String email, String fName, String lName, String bio, String dob) {
        this.userId = userId;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.bio = bio;
        this.dob = dob;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getBio() {
        return bio;
    }

    public String getDob() {
        return dob;
    }
}
