package com.mycontactsapp.user.service;

import com.mycontactsapp.user.UserRepository;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.validation.EmailValidator;
import com.mycontactsapp.exception.UserAlreadyExistsException;

public class RegistrationService {

    private UserRepository repository;

    public RegistrationService(UserRepository repository){
        this.repository = repository;
    }

    public void register(User user) throws UserAlreadyExistsException {

        if(!EmailValidator.isValid(user.getEmail()))
            throw new IllegalArgumentException("Invalid email format");

        if(repository.exists(user.getEmail()))
            throw new UserAlreadyExistsException("User already exists");

        repository.save(user);
    }
}