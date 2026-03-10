/*
// - Use Case-4: Create Contact
// - User adds a new contact with name, phone numbers, email addresses
// - It uses list,LocalDateTime for timestamps, UUID for unique IDs
// - @author Developer
// - @version 4.0
*/
package com.mycontactsapp;

import com.mycontactsapp.user.UserBuilder;
import com.mycontactsapp.user.UserRepository;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.RegistrationService;

import com.mycontactsapp.auth.strategy.*;
import com.mycontactsapp.auth.session.SessionManager;

import com.mycontactsapp.profile.command.*;
import com.mycontactsapp.profile.service.*;

import com.mycontactsapp.contacts.*;

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
            System.out.println("3. Manage Profile");
            System.out.println("4. Create Contact");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
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
                	manageProfile(sc, session);
                	break;
                    
                case 4:
                	createContact(sc);
                	break;                	
                    
                case 5:
                	session.logout();
                    System.out.println("Logged out successfully.");
                    break;
                    
                case 6:
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
    private static void manageProfile(Scanner sc, SessionManager session){

        if(!session.isLoggedIn()){
            System.out.println("Please login first.");
            return;
        }

        User user = session.getCurrentUser();

        ProfileService service = new ProfileService();

        System.out.println("\nProfile Menu");
        System.out.println("1. Change Name");
        System.out.println("2. Change Password");
        System.out.println("3. View Profile");

        int choice = Integer.parseInt(sc.nextLine());

        switch(choice){

            case 1:

                System.out.println("Enter new name:");
                String newName = sc.nextLine();

                service.executeCommand(
                        new UpdateNameCommand(user,newName)
                );

                System.out.println("Name updated successfully.");
                break;

            case 2:

                System.out.println("Enter new password:");
                String newPassword = sc.nextLine();

                service.executeCommand(
                        new ChangePasswordCommand(user,newPassword)
                );

                System.out.println("Password changed successfully.");
                break;

            case 3:
                System.out.println(user);
                break;

            default:
                System.out.println("Invalid option.");
        }
    }
    
    private static void createContact(Scanner sc){

        System.out.println("\nContact Type (person/organization):");
        String type = sc.nextLine();

        System.out.println("Name:");
        String name = sc.nextLine();

        String orgName = null;

        if(type.equalsIgnoreCase("organization")){
            System.out.println("Organization Name:");
            orgName = sc.nextLine();
        }

        ContactBuilder builder = new ContactBuilder()
                .setType(type)
                .setName(name)
                .setOrganizationName(orgName);

        System.out.println("Enter phone:");
        builder.addPhone(sc.nextLine());

        System.out.println("Enter email:");
        builder.addEmail(sc.nextLine());

        Contact contact = builder.build();

        System.out.println("\nContact Created:");
        contact.display();
    }
}