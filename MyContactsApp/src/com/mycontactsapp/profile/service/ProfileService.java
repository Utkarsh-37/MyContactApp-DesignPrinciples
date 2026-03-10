package com.mycontactsapp.profile.service;

import com.mycontactsapp.profile.command.ProfileCommand;

public class ProfileService {

    public void executeCommand(ProfileCommand command){
        command.execute();
    }
}
