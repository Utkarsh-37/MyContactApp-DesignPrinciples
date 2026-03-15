package com.mycontactsapp.contacts;

import java.time.LocalDateTime;
import java.util.*;

import com.mycontactsapp.contacts.tag.*;

public abstract class Contact {

    protected UUID id;
    protected String name;
    protected List<String> phones;
    protected List<String> emails;
    protected LocalDateTime createdAt;

    private boolean deleted = false;

    private Set<Tag> tags = new HashSet<>();

    private List<TagObserver> observers = new ArrayList<>();


    // Constructor
    public Contact(String name, List<String> phones, List<String> emails) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phones = new ArrayList<>(phones);
        this.emails = new ArrayList<>(emails);
        this.createdAt = LocalDateTime.now();
    }


    // Copy constructor (for Memento / Undo)
    public Contact(Contact other){
        this.id = other.id;
        this.name = other.name;
        this.phones = new ArrayList<>(other.phones);
        this.emails = new ArrayList<>(other.emails);
        this.tags = new HashSet<>(other.tags);
        this.createdAt = other.createdAt;
    }


    // Abstract methods
    public abstract void display();

    public abstract Contact copy();


    // Getters
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

    public Set<Tag> getTags(){
        return tags;
    }


    // Setters with validation
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


    // Tag management (UC11 + UC12)

    public void addTag(Tag tag){

        if(tags.add(tag)){
            notifyObservers(tag);
        }
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }


    // Observer methods (UC12)

    public void addObserver(TagObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(Tag tag){

        for(TagObserver observer : observers){
            observer.onTagChanged(this, tag);
        }
    }


    // Lifecycle management (UC7)

    public boolean isDeleted() {
        return deleted;
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void hardDelete() {
        phones.clear();
        emails.clear();
        tags.clear();
    }


    // Display formatting (UC5)

    @Override
    public String toString() {

        String tagText = tags.isEmpty() ? "None" : tags.toString();

        return String.format(
                "ID: %s\nName: %s\nPhones: %s\nEmails: %s\nTags: %s\nCreated: %s",
                id,
                name,
                phones,
                emails,
                tagText,
                createdAt
        );
    }
}