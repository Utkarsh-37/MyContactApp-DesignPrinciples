package com.mycontactsapp.contacts;

import java.util.List;

public class OrganizationContact extends Contact {

    private String organizationName;

    public OrganizationContact(String name, String organizationName,
                               List<String> phones, List<String> emails) {

        super(name, phones, emails);
        this.organizationName = organizationName;
    }

    @Override
    public void display() {
        System.out.println("Organization Contact");
        System.out.println("Contact Person: " + name);
        System.out.println("Organization: " + organizationName);
        System.out.println("Phones: " + phones);
        System.out.println("Emails: " + emails);
    }
}