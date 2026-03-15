package com.mycontactsapp.contacts.search;

import com.mycontactsapp.contacts.Contact;

import java.util.regex.Pattern;

public class EmailSpecification implements ContactSpecification {

    private Pattern pattern;

    public EmailSpecification(String keyword){
        pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean isSatisfied(Contact contact){

        return contact.getEmails()
                .stream()
                .anyMatch(email -> pattern.matcher(email).find());
    }
}
