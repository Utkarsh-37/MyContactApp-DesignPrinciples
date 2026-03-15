package com.mycontactsapp.contacts.composite;

import com.mycontactsapp.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactGroup implements ContactComponent {

    private String groupName;

    private List<Contact> contacts = new ArrayList<>();

    public ContactGroup(String groupName){
        this.groupName = groupName;
    }

    public void add(Contact contact){
        contacts.add(contact);
    }

    public List<Contact> getContacts(){
        return contacts;
    }

    @Override
    public void display(){

        System.out.println("Group: " + groupName);

        contacts.forEach(Contact::display);
    }

    @Override
    public Contact getContact(){
        return null;
    }
}
