package com.mycontactsapp.contacts.decorator;

import com.mycontactsapp.contacts.Contact;

public abstract class ContactDecorator extends Contact {

    protected Contact contact;

    public ContactDecorator(Contact contact) {
        super(contact.getName(), contact.getPhones(), contact.getEmails());
        this.contact = contact;
    }

    @Override
    public String toString() {
        return contact.toString();
    }

    @Override
    public void display() {
        System.out.println(this);
    }
}
