package com.mycontactsapp.profile.command;

import com.mycontactsapp.user.model.User;

public class ChangePasswordCommand implements ProfileCommand {

    private User user;
    private String newPassword;

    public ChangePasswordCommand(User user, String newPassword){
        this.user = user;
        this.newPassword = newPassword;
    }

    @Override
    public void execute(){
        user.changePassword(newPassword);
    }
}