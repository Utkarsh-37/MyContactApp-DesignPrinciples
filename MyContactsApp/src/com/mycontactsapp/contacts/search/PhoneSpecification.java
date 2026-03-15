package com.mycontactsapp.contacts.search;

import com.mycontactsapp.contacts.Contact;

public class PhoneSpecification implements ContactSpecification {

    private String phone;

    public PhoneSpecification(String phone){
        this.phone = phone;
    }

    @Override
    public boolean isSatisfied(Contact contact){

        return contact.getPhones()
                .stream()
                .anyMatch(p -> p.contains(phone));
    }
}