/*
 // - Use Case-11: Create and Manage Tags
 // - User creates custom tags (Family, Work, Friends) for organizing contacts.
 //
 // - Implements Creating, viewing and deleting from a central list of tags.
 // 
 // - @author Developer
 // - @version 11.0
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
import com.mycontactsapp.contacts.command.*;
import com.mycontactsapp.contacts.decorator.*;
import com.mycontactsapp.contacts.filter.*;
import com.mycontactsapp.contacts.observer.*;
import com.mycontactsapp.contacts.search.*;
import com.mycontactsapp.contacts.tag.*;
import com.mycontactsapp.validation.EmailValidator;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		UserRepository repository = new UserRepository();
		RegistrationService regService = new RegistrationService(repository);

		SessionManager session = SessionManager.getInstance();

		List<Contact> contacts = new ArrayList<>();

		CommandManager commandManager = new CommandManager();
		
		List<ContactObserver> observers = new ArrayList<>();
		observers.add(new ContactDeletionLogger());

		while (true) {

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
			System.out.println("11. Advance Filter");
			System.out.println("12. Manage Tags");
			System.out.println("13. Logout");
			System.out.println("14. Exit");
			System.out.print("Choose option: ");

			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {

			case 1 -> register(sc, regService);

			case 2 -> login(sc, repository, session);

			case 3 -> manageProfile(sc, session);

			case 4 -> contacts.add(createContact(sc));

			case 5 -> viewContacts(contacts, sc);

			case 6 -> editContact(contacts, sc, commandManager);

			case 7 -> commandManager.undo();
			
			case 8 -> deleteContact(contacts, sc, observers);
			
			case 9 -> bulkOperations(contacts, sc);
			
			case 10 -> searchContacts(contacts, sc);
			
			case 11 -> advancedFilter(contacts, sc);
			
			case 12 -> manageTags(contacts, sc);

			case 13 -> {
				session.logout();
				System.out.println("Logged out successfully.");
			}

			case 14 -> {
				System.out.println("Exiting application...");
				return;
			}

			default -> System.out.println("Invalid option.");
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

	private static Contact createContact(Scanner sc){

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

		return contact;
	}

	private static void viewContacts(List<Contact> contacts, Scanner sc){

		if(contacts.isEmpty()){
			System.out.println("No contacts available.");
			return;
		}

		System.out.println("\nYour Contacts:");

		for(int i = 0; i < contacts.size(); i++){
			System.out.println((i+1) + ". " + contacts.get(i).getName());
		}

		System.out.println("Select contact number:");
		int index = Integer.parseInt(sc.nextLine()) - 1;

		if(index < 0 || index >= contacts.size()){
			System.out.println("Invalid contact.");
			return;
		}

		Contact contact = contacts.get(index);

		System.out.println("\nView Format");
		System.out.println("1. Normal");
		System.out.println("2. Mask Email");

		int option = Integer.parseInt(sc.nextLine());

		if(option == 2){
			contact = new MaskEmailDecorator(contact);
		}

		System.out.println("\nContact Details:\n");
		System.out.println(contact);
	}

	private static void editContact(List<Contact> contacts,Scanner sc,CommandManager manager){

		if(contacts.isEmpty()){
			System.out.println("No contacts available.");
			return;
		}

		System.out.println("\nSelect Contact:");

		for(int i=0;i<contacts.size();i++){
			System.out.println((i+1)+". "+contacts.get(i).getName());
		}

		int index = Integer.parseInt(sc.nextLine())-1;

		if(index<0 || index>=contacts.size()){
			System.out.println("Invalid contact.");
			return;
		}

		Contact contact = contacts.get(index);

		System.out.println("Enter new name:");
		String name = sc.nextLine();

		System.out.println("Enter new phone:");
		List<String> phones = List.of(sc.nextLine());

		System.out.println("Enter new email:");
		List<String> emails = List.of(sc.nextLine());

		EditContactCommand command =
				new EditContactCommand(contact,name,phones,emails);

		manager.executeCommand(command);

		System.out.println("Contact updated.");
	}
	private static void deleteContact(List<Contact> contacts,
			Scanner sc,
			List<ContactObserver> observers){

		if(contacts.isEmpty()){
			System.out.println("No contacts available.");
			return;
		}

		System.out.println("\nSelect Contact to Delete:");

		for(int i=0;i<contacts.size();i++){
			System.out.println((i+1)+". "+contacts.get(i).getName());
		}

		int index = Integer.parseInt(sc.nextLine())-1;

		if(index<0 || index>=contacts.size()){
			System.out.println("Invalid contact.");
			return;
		}

		Contact contact = contacts.get(index);

		System.out.println("Delete Type:");
		System.out.println("1 Soft Delete");
		System.out.println("2 Hard Delete");

		int type = Integer.parseInt(sc.nextLine());

		System.out.println("Are you sure? (yes/no)");
		String confirm = sc.nextLine();

		if(!confirm.equalsIgnoreCase("yes")){
			System.out.println("Deletion cancelled.");
			return;
		}

		try{

			if(type == 1){
				contact.softDelete();
			} else {
				contact.hardDelete();
				contacts.remove(contact);
			}

			for(ContactObserver observer : observers){
				observer.onContactDeleted(contact);
			}

			System.out.println("Contact deleted successfully.");

		} catch(Exception e){
			System.out.println("Error deleting contact.");
		}
	}
	
	private static void bulkOperations(List<Contact> contacts, Scanner sc){

	    if(contacts.isEmpty()){
	        System.out.println("No contacts available.");
	        return;
	    }

	    System.out.println("\nBulk Operation Menu");
	    System.out.println("1 Delete contacts starting with letter");
	    System.out.println("2 Export contacts");
	    System.out.println("3 Tag contacts");

	    int choice = Integer.parseInt(sc.nextLine());

	    switch(choice){

	        case 1 -> {

	            System.out.println("Enter starting letter:");
	            String letter = sc.nextLine();

	            contacts.removeIf(c -> c.getName().startsWith(letter));

	            System.out.println("Contacts deleted.");
	        }

	        case 2 -> {

	            System.out.println("Exporting contacts...");

	            contacts.stream()
	                    .map(Contact::getName)
	                    .forEach(System.out::println);
	        }

	        case 3 -> {

	            System.out.println("Enter tag:");
	            String tag = sc.nextLine();

	            contacts.forEach(c ->
	                    System.out.println(c.getName()+" tagged as "+tag)
	            );
	        }

	        default -> System.out.println("Invalid option.");
	    }
	}
	
	private static void searchContacts(List<Contact> contacts, Scanner sc){

	    if(contacts.isEmpty()){
	        System.out.println("No contacts available.");
	        return;
	    }

	    System.out.println("\nSearch By");
	    System.out.println("1 Name");
	    System.out.println("2 Phone");
	    System.out.println("3 Email");

	    int choice = Integer.parseInt(sc.nextLine());

	    System.out.println("Enter search keyword:");
	    String keyword = sc.nextLine();

	    ContactSpecification spec;

	    switch(choice){

	        case 1 -> spec = new NameSpecification(keyword);

	        case 2 -> spec = new PhoneSpecification(keyword);

	        case 3 -> spec = new EmailSpecification(keyword);

	        default -> {
	            System.out.println("Invalid option.");
	            return;
	        }
	    }

	    System.out.println("\nSearch Results:\n");

	    contacts.stream()
	            .filter(spec::isSatisfied)
	            .forEach(Contact::display);
	}
	
	private static void advancedFilter(List<Contact> contacts, Scanner sc){

	    if(contacts.isEmpty()){
	        System.out.println("No contacts available.");
	        return;
	    }

	    CompositeFilter filter = new CompositeFilter();

	    System.out.println("Filter by name? (y/n)");
	    if(sc.nextLine().equalsIgnoreCase("y")){
	        System.out.println("Enter name keyword:");
	        filter.addFilter(new NameFilter(sc.nextLine()));
	    }

	    System.out.println("\nFiltered Results:");

	    contacts.stream()
	            .filter(filter::apply)
	            .sorted(Comparator.comparing(Contact::getName))
	            .forEach(Contact::display);
	}
	
	private static void manageTags(List<Contact> contacts, Scanner sc){

	    if(contacts.isEmpty()){
	        System.out.println("No contacts available.");
	        return;
	    }

	    System.out.println("\nSelect Contact:");

	    for(int i=0;i<contacts.size();i++){
	        System.out.println((i+1)+". "+contacts.get(i).getName());
	    }

	    int index = Integer.parseInt(sc.nextLine()) - 1;

	    if(index < 0 || index >= contacts.size()){
	        System.out.println("Invalid contact.");
	        return;
	    }

	    Contact contact = contacts.get(index);

	    System.out.println("Enter tag name:");
	    String tagName = sc.nextLine();

	    Tag tag = TagFactory.getTag(tagName);

	    contact.addTag(tag);

	    System.out.println("Tag added successfully.");
	}
}