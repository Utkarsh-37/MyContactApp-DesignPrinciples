package com.mycontactsapp.contacts.observer;

import com.mycontactsapp.contacts.Contact;

public interface ContactObserver {

    void onContactDeleted(Contact contact);
}
