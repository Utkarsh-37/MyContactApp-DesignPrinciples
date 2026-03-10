package com.mycontactsapp.user.factory;

import com.mycontactsapp.user.model.*;
import com.mycontactsapp.security.PasswordHasher;

public class UserFactory {

    public static User createUser(String type, String email, String password, String name) {

        String hash = PasswordHasher.hash(password);

        if(type.equalsIgnoreCase("premium"))
            return new PremiumUser(email, hash, name);

        return new FreeUser(email, hash, name);
    }
}