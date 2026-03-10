package com.mycontactsapp.auth.service;

import com.mycontactsapp.auth.strategy.AuthenticationStrategy;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.repository.UserRepository;

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