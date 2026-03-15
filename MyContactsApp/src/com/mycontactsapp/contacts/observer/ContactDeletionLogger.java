package com.mycontactsapp.contacts.observer;

import com.mycontactsapp.contacts.Contact;

public class ContactDeletionLogger implements ContactObserver {

    @Override
    public void onContactDeleted(Contact contact) {
        System.out.println("Observer: Contact deleted -> " + contact.getName());
    }
}
