package com.mycontactsapp.contacts.command;

import com.mycontactsapp.contacts.Contact;

import java.util.List;

public class EditContactCommand implements ContactCommand {

    private Contact contact;

    private Contact backup;

    private String newName;
    private List<String> newPhones;
    private List<String> newEmails;

    public EditContactCommand(Contact contact,
                              String newName,
                              List<String> newPhones,
                              List<String> newEmails){

        this.contact = contact;

        this.newName = newName;
        this.newPhones = newPhones;
        this.newEmails = newEmails;
    }

    @Override
    public void execute(){

    	backup = contact.copy(); // Memento

        contact.setName(newName);
        contact.setPhones(newPhones);
        contact.setEmails(newEmails);
    }

    @Override
    public void undo(){

        contact.setName(backup.getName());
        contact.setPhones(backup.getPhones());
        contact.setEmails(backup.getEmails());
    }
}