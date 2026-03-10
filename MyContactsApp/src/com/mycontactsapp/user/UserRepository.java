package com.mycontactsapp.user;

import com.mycontactsapp.user.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public boolean exists(String email) {
        return users.containsKey(email);
    }
}