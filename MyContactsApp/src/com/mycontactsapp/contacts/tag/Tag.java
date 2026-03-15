package com.mycontactsapp.contacts.tag;

import java.util.Objects;

public class Tag {

    private String name;

    public Tag(String name){

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Tag cannot be empty");

        this.name = name.toLowerCase();
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o){

        if(this == o) return true;
        if(!(o instanceof Tag tag)) return false;

        return name.equals(tag.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return name;
    }
}
