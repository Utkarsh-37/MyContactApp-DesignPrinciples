package com.mycontactsapp.contacts.tag;

import java.util.HashMap;
import java.util.Map;

public class TagFactory {

    private static Map<String, Tag> tagPool = new HashMap<>();

    public static Tag getTag(String name){

        name = name.toLowerCase();

        return tagPool.computeIfAbsent(name, Tag::new);
    }
}
