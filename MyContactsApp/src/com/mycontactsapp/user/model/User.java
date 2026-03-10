package com.mycontactsapp.user.model;

import com.mycontactsapp.validation.PasswordHasher;

public class User {

    private String email;
    private String passwordHash;
    private String name;

    public User(String email, String passwordHash, String name) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
    }

    // JavaBeans getters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    // Setter with validation
    public void setName(String name) {

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        this.name = name;
    }

    public void changePassword(String newPassword){

        if(newPassword.length() < 4)
            throw new IllegalArgumentException("Password too short");

        this.passwordHash = PasswordHasher.hash(newPassword);
    }

    public boolean checkPassword(String password){
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