package com.mycontactsapp.contacts.filter;

import com.mycontactsapp.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements ContactFilter {

    private List<ContactFilter> filters = new ArrayList<>();

    public void addFilter(ContactFilter filter){
        filters.add(filter);
    }

    @Override
    public boolean apply(Contact contact) {

        return filters.stream()
                .allMatch(f -> f.apply(contact));
    }
}