package com.mycontactsapp.auth.strategy;

import com.mycontactsapp.user.UserRepository;
import com.mycontactsapp.user.model.User;

import java.util.Optional;

public class AuthenticationService {

    private AuthenticationStrategy strategy;
    private UserRepository repository;

    public AuthenticationService(AuthenticationStrategy strategy, UserRepository repository){
        this.strategy = strategy;
        this.repository = repository;
    }

    public Optional<User> login(String email, String password){
        return strategy.authenticate(email, password, repository);
    }
}