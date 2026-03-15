package com.mycontactsapp.contacts.filter;

import com.mycontactsapp.contacts.Contact;

public interface ContactFilter {

    boolean apply(Contact contact);
}