package com.mycontactsapp.auth.strategy;

import com.mycontactsapp.user.UserRepository;
import com.mycontactsapp.user.model.User;

import java.util.Optional;

public interface AuthenticationStrategy {

    Optional<User> authenticate(String email, String password, UserRepository repository);
}
