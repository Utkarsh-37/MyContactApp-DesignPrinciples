package com.mycontactsapp.contacts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public abstract class Contact {

    protected UUID id;
    protected String name;
    protected List<String> phones;
    protected List<String> emails;
    protected LocalDateTime createdAt;

    public Contact(String name, List<String> phones, List<String> emails) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phones = phones;
        this.emails = emails;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public abstract void display();
}
