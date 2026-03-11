package com.mycontactsapp.contacts.decorator;

import com.mycontactsapp.contacts.Contact;

public class MaskEmailDecorator extends ContactDecorator {

    public MaskEmailDecorator(Contact contact) {
        super(contact);
    }

    @Override
    public String toString() {

        String text = contact.toString();

        return text.replaceAll("(.{2}).+(@.+)", "$1***$2");
    }
}
