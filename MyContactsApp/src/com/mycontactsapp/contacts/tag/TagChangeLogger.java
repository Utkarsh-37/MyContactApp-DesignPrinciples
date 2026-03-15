package com.mycontactsapp.contacts.tag;

import com.mycontactsapp.contacts.Contact;

public class TagChangeLogger implements TagObserver {

    @Override
    public void onTagChanged(Contact contact, Tag tag) {

        System.out.println(
                "Observer: Tag '" + tag + "' added to " + contact.getName()
        );
    }
}