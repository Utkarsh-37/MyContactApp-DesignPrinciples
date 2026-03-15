package com.mycontactsapp.contacts.command;

public interface ContactCommand {

    void execute();

    void undo();
}
