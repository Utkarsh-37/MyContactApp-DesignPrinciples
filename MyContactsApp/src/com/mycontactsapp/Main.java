/*
// - Use Case-1: User Registartion
// - User creates an account with email, password, and profile information
// - Input is validated
// - If it is a success user details are displayed
// - @author Developer
// - @version 1.0
*/
package com.mycontactsapp;

import com.mycontactsapp.user.builder.UserBuilder;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.repository.UserRepository;
import com.mycontactsapp.user.service.RegistrationService;
import com.mycontactsapp.validation.EmailValidator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserRepository repository = new UserRepository();
        RegistrationService service = new RegistrationService(repository);

        System.out.print("Enter Name:");
        String name = sc.nextLine();

        System.out.print("Enter Email:");
        String email = sc.nextLine();

        if(!EmailValidator.isValid(email)){
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Enter Password:");
        String password = sc.nextLine();

        System.out.print("User Type (free/premium):");
        String type = sc.nextLine();

        try {

            User user = new UserBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setPassword(password)
                    .setType(type)
                    .build();

            service.register(user);

            System.out.println("\nUser Registered Successfully!\n");
            System.out.println(user);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}