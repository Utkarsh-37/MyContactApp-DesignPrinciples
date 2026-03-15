/*
 // - Use Case-12: Apply Tags to Contacts
 // - User assigns one or multiple tags to contacts.
 // 
 // - @author Developer
 // - @version 12.0
*/
package com.mycontactsapp;

import com.mycontactsapp.user.*;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.RegistrationService;

import com.mycontactsapp.auth.strategy.*;
import com.mycontactsapp.auth.session.SessionManager;

import com.mycontactsapp.profile.command.*;
import com.mycontactsapp.profile.service.*;

import com.mycontactsapp.contacts.*;
import com.mycontactsapp.contacts.command.*;
import com.mycontactsapp.contacts.decorator.*;
import com.mycontactsapp.contacts.filter.*;
import com.mycontactsapp.contacts.observer.*;
import com.mycontactsapp.contacts.search.*;
import com.mycontactsapp.contacts.tag.*;

import com.mycontactsapp.validation.EmailValidator;

import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final UserRepository repository = new UserRepository();
    private static final RegistrationService regService = new RegistrationService(repository);

    private static final SessionManager session = SessionManager.getInstance();

    private static final List<Contact> contacts = new ArrayList<>();

    private static final CommandManager commandManager = new CommandManager();

    private static final List<ContactObserver> deleteObservers =
            List.of(new ContactDeletionLogger());

    private static final TagObserver tagObserver = new TagChangeLogger();


    public static void main(String[] args) {

        while (true) {

            printMenu();

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1 -> register();
                case 2 -> login();
                case 3 -> manageProfile();
                case 4 -> createContact();
                case 5 -> viewContacts();
                case 6 -> editContact();
                case 7 -> commandManager.undo();
                case 8 -> deleteContact();
                case 9 -> bulkOperations();
                case 10 -> searchContacts();
                case 11 -> advancedFilter();
                case 12 -> manageTags();
                case 13 -> logout();
                case 14 -> exit();

                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {

        System.out.println("\n===== MyContacts App =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Manage Profile");
        System.out.println("4. Create Contact");
        System.out.println("5. View Contact");
        System.out.println("6. Edit Contact");
        System.out.println("7. Undo Last Edit");
        System.out.println("8. Delete Contact");
        System.out.println("9. Bulk Operations");
        System.out.println("10. Search Contacts");
        System.out.println("11. Advanced Filter");
        System.out.println("12. Manage Tags");
        System.out.println("13. Logout");
        System.out.println("14. Exit");
        System.out.print("Choose option: ");
    }

    private static void register() {

        try {

            System.out.println("Enter Name:");
            String name = sc.nextLine();

            System.out.println("Enter Email:");
            String email = sc.nextLine();

            if (!EmailValidator.isValid(email)) {
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

            System.out.println("User Registered Successfully!");
            System.out.println(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void login() {

        System.out.println("1 Basic Authentication");
        System.out.println("2 OAuth Login");

        int method = Integer.parseInt(sc.nextLine());

        AuthenticationStrategy strategy =
                method == 2 ? new OAuthStrategy() : new BasicAuthStrategy();

        AuthenticationService authService =
                new AuthenticationService(strategy, repository);

        System.out.println("Enter Email:");
        String email = sc.nextLine();

        String password = "";

        if (method == 1) {
            System.out.println("Enter Password:");
            password = sc.nextLine();
        }

        Optional<User> user = authService.login(email, password);

        user.ifPresentOrElse(
                u -> {
                    session.login(u);
                    System.out.println("Welcome " + u.getName());
                },
                () -> System.out.println("Authentication failed.")
        );
    }

    private static void manageProfile() {

        if (!session.isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        User user = session.getCurrentUser();

        ProfileService service = new ProfileService();

        System.out.println("1 Change Name");
        System.out.println("2 Change Password");
        System.out.println("3 View Profile");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {

            case 1 -> {
                System.out.println("Enter new name:");
                service.executeCommand(new UpdateNameCommand(user, sc.nextLine()));
            }

            case 2 -> {
                System.out.println("Enter new password:");
                service.executeCommand(new ChangePasswordCommand(user, sc.nextLine()));
            }

            case 3 -> System.out.println(user);
        }
    }

    private static void createContact() {

        System.out.println("Contact Type (person/organization):");
        String type = sc.nextLine();

        System.out.println("Name:");
        String name = sc.nextLine();

        String orgName = null;

        if (type.equalsIgnoreCase("organization")) {
            System.out.println("Organization Name:");
            orgName = sc.nextLine();
        }

        ContactBuilder builder = new ContactBuilder()
                .setType(type)
                .setName(name)
                .setOrganizationName(orgName);

        System.out.println("Phone:");
        builder.addPhone(sc.nextLine());

        System.out.println("Email:");
        builder.addEmail(sc.nextLine());

        Contact contact = builder.build();
        contact.addObserver(tagObserver);

        contacts.add(contact);

        System.out.println("Contact created.");
    }

    private static Contact selectContact() {

        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return null;
        }

        for (int i = 0; i < contacts.size(); i++)
            System.out.println((i + 1) + ". " + contacts.get(i).getName());

        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= contacts.size())
            return null;

        return contacts.get(index);
    }

    private static void viewContacts() {

        Contact contact = selectContact();
        if (contact == null) return;

        System.out.println("1 Normal");
        System.out.println("2 Mask Email");

        int option = Integer.parseInt(sc.nextLine());

        if (option == 2)
            contact = new MaskEmailDecorator(contact);

        System.out.println(contact);
    }

    private static void editContact() {

        Contact contact = selectContact();
        if (contact == null) return;

        System.out.println("New Name:");
        String name = sc.nextLine();

        System.out.println("New Phone:");
        List<String> phones = List.of(sc.nextLine());

        System.out.println("New Email:");
        List<String> emails = List.of(sc.nextLine());

        commandManager.executeCommand(
                new EditContactCommand(contact, name, phones, emails));
    }

    private static void deleteContact() {

        Contact contact = selectContact();
        if (contact == null) return;

        System.out.println("1 Soft Delete");
        System.out.println("2 Hard Delete");

        int type = Integer.parseInt(sc.nextLine());

        if (type == 1)
            contact.softDelete();
        else
            contacts.remove(contact);

        deleteObservers.forEach(o -> o.onContactDeleted(contact));
    }

    private static void bulkOperations() {

        System.out.println("Delete contacts starting with letter:");
        String letter = sc.nextLine();

        contacts.removeIf(c -> c.getName().startsWith(letter));
    }

    private static void searchContacts() {

        System.out.println("1 Name");
        System.out.println("2 Phone");
        System.out.println("3 Email");

        int choice = Integer.parseInt(sc.nextLine());

        System.out.println("Keyword:");
        String keyword = sc.nextLine();

        ContactSpecification spec =
                switch (choice) {
                    case 1 -> new NameSpecification(keyword);
                    case 2 -> new PhoneSpecification(keyword);
                    case 3 -> new EmailSpecification(keyword);
                    default -> null;
                };

        if (spec == null) return;

        contacts.stream()
                .filter(spec::isSatisfied)
                .forEach(Contact::display);
    }

    private static void advancedFilter() {

        CompositeFilter filter = new CompositeFilter();

        System.out.println("Filter by name? (y/n)");

        if (sc.nextLine().equalsIgnoreCase("y"))
            filter.addFilter(new NameFilter(sc.nextLine()));

        contacts.stream()
                .filter(filter::apply)
                .sorted(Comparator.comparing(Contact::getName))
                .forEach(Contact::display);
    }

    private static void manageTags() {

        Contact contact = selectContact();
        if (contact == null) return;

        System.out.println("1 Add Tags");
        System.out.println("2 Remove Tag");

        int option = Integer.parseInt(sc.nextLine());

        if (option == 1) {

            System.out.println("Tags (comma separated):");

            String[] names = sc.nextLine().split(",");

            for (String name : names)
                contact.addTag(TagFactory.getTag(name.trim()));

        } else {

            System.out.println("Current tags: " + contact.getTags());

            Tag tag = TagFactory.getTag(sc.nextLine());

            contact.removeTag(tag);
        }
    }

    private static void logout() {
        session.logout();
        System.out.println("Logged out.");
    }

    private static void exit() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}