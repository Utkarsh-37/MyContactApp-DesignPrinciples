package com.mycontactsapp.contacts;

import java.time.LocalDateTime;
import java.util.*;

import com.mycontactsapp.contacts.tag.Tag;

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
    
    public Contact(Contact other){
        this.id = other.id;
        this.name = other.name;
        this.phones = new ArrayList<>(other.phones);
        this.emails = new ArrayList<>(other.emails);
        this.createdAt = other.createdAt;
    }

    public void setName(String name){

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        this.name = name;
    }

    public void setPhones(List<String> phones){
        this.phones = new ArrayList<>(phones);
    }

    public void setEmails(List<String> emails){
        this.emails = new ArrayList<>(emails);
    }
    
    @Override
    public String toString() {

        return String.format(
                "ID: %s\nName: %s\nPhones: %s\nEmails: %s\nTags: %s\nCreated: %s",
                id,
                name,
                phones,
                emails,
                tags,
                createdAt
        );
    }
    
    public abstract Contact copy();
    
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void hardDelete() {
        phones.clear();
        emails.clear();
    }
    
    private Set<Tag> tags = new HashSet<>();
    public void addTag(Tag tag){
        tags.add(tag);
    }

    public Set<Tag> getTags(){
        return tags;
    }
}
