package com.mycontactsapp.contacts.tag;

import com.mycontactsapp.contacts.Contact;

public class ContactTag {

    private Contact contact;
    private Tag tag;

    public ContactTag(Contact contact, Tag tag){
        this.contact = contact;
        this.tag = tag;
    }

    public Contact getContact(){
        return contact;
    }

    public Tag getTag(){
        return tag;
    }
}
