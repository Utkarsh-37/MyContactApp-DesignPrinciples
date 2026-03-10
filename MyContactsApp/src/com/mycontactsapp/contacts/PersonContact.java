package com.mycontactsapp.contacts;

import java.util.List;

public class PersonContact extends Contact {

    public PersonContact(String name, List<String> phones, List<String> emails) {
        super(name, phones, emails);
    }

    @Override
    public void display() {
        System.out.println("Person Contact");
        System.out.println("Name: " + name);
        System.out.println("Phones: " + phones);
        System.out.println("Emails: " + emails);
    }
}
