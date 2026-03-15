package com.mycontactsapp.contacts.filter;

import com.mycontactsapp.contacts.Contact;

public class NameFilter implements ContactFilter {

    private String keyword;

    public NameFilter(String keyword){
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean apply(Contact contact) {

        return contact.getName()
                .toLowerCase()
                .contains(keyword);
    }
}