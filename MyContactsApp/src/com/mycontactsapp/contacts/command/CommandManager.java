package com.mycontactsapp.contacts.command;

import java.util.Stack;

public class CommandManager {

    private Stack<ContactCommand> history = new Stack<>();

    public void executeCommand(ContactCommand command){

        command.execute();
        history.push(command);
    }

    public void undo(){

        if(!history.isEmpty()){
            history.pop().undo();
        }
    }
}