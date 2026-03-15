package com.mycontactsapp.contacts.search;

import com.mycontactsapp.contacts.Contact;

public class NameSpecification implements ContactSpecification {

    private String keyword;

    public NameSpecification(String keyword){
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean isSatisfied(Contact contact){

        return contact.getName()
                .toLowerCase()
                .contains(keyword);
    }
}