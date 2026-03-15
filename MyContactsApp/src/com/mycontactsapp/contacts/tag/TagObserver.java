package com.mycontactsapp.contacts.tag;

import com.mycontactsapp.contacts.Contact;

public interface TagObserver {

    void onTagChanged(Contact contact, Tag tag);
}
