/*
// - Use Case-2: User Authentication
// - User logs in with credentials to access their contact list
// - Password is hashed using MessageDigest (SHA-256)
// - User can select to login using BasicAuth or OAuth
// - @author Developer
// - @version 2.0
*/
package com.mycontactsapp;

import com.mycontactsapp.user.builder.UserBuilder;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.repository.UserRepository;
import com.mycontactsapp.user.service.RegistrationService;

import com.mycontactsapp.auth.strategy.*;
import com.mycontactsapp.auth.service.AuthenticationService;
import com.mycontactsapp.auth.session.SessionManager;

import com.mycontactsapp.validation.EmailValidator;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserRepository repository = new UserRepository();
        RegistrationService regService = new RegistrationService(repository);

        SessionManager session = SessionManager.getInstance();

        while(true){

            System.out.println("\n===== MyContacts App =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch(choice){

                case 1:
                    register(sc, regService);
                    break;

                case 2:
                    login(sc, repository, session);
                    break;

                case 3:
                    session.logout();
                    System.out.println("Logged out successfully.");
                    break;

                case 4:
                    System.out.println("Exiting application...");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void register(Scanner sc, RegistrationService regService){

        try {

            System.out.println("\nEnter Name:");
            String name = sc.nextLine();

            System.out.println("Enter Email:");
            String email = sc.nextLine();

            if(!EmailValidator.isValid(email)){
                System.out.println("Invalid email format.");
                return;
            }

            System.out.println("Enter Password:");
            String password = sc.nextLine();

            System.out.println("User Type (free/premium):");
            String type = sc.nextLine();

            User user = new UserBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setPassword(password)
                    .setType(type)
                    .build();

            regService.register(user);

            System.out.println("\nUser Registered Successfully!");
            System.out.println(user);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    private static void login(Scanner sc, UserRepository repository, SessionManager session){

        System.out.println("\nChoose Authentication Method");
        System.out.println("1. Basic Authentication");
        System.out.println("2. OAuth Login");

        int method = Integer.parseInt(sc.nextLine());

        AuthenticationStrategy strategy;

        if(method == 2)
            strategy = new OAuthStrategy();
        else
            strategy = new BasicAuthStrategy();

        AuthenticationService authService =
                new AuthenticationService(strategy, repository);

        System.out.println("Enter Email:");
        String email = sc.nextLine();

        String password = "";

        if(method == 1){
            System.out.println("Enter Password:");
            password = sc.nextLine();
        }

        Optional<User> user = authService.login(email, password);

        if(user.isPresent()){

            session.login(user.get());

            System.out.println("\nLogin Successful!");
            System.out.println("Welcome " + session.getCurrentUser().getName());

        } else {
            System.out.println("Authentication failed.");
        }
    }
}