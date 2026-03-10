package com.mycontactsapp.user.model;

import com.mycontactsapp.security.PasswordHasher;

public class User {

    private String email;
    private String passwordHash;
    private String name;

    public User(String email, String passwordHash, String name) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean checkPassword(String password) {
        return PasswordHasher.hash(password).equals(passwordHash);
    }

    @Override
    public String toString() {
        return "User Details:\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Account Type: " + this.getClass().getSimpleName();
    }
}