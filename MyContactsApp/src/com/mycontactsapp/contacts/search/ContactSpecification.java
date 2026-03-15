package com.mycontactsapp.contacts.search;

import com.mycontactsapp.contacts.Contact;

public interface ContactSpecification {

    boolean isSatisfied(Contact contact);
}
