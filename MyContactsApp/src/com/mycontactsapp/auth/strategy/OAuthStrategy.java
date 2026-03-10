package com.mycontactsapp.auth.strategy;

import com.mycontactsapp.user.UserRepository;
import com.mycontactsapp.user.model.User;

import java.util.Optional;

public class OAuthStrategy implements AuthenticationStrategy {

    @Override
    public Optional<User> authenticate(String email, String password, UserRepository repository) {

        User user = repository.findByEmail(email);

        if(user != null)
            return Optional.of(user);

        return Optional.empty();
    }
}
