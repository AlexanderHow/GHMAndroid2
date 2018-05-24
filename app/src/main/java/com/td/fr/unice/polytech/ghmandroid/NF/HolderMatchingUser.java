package com.td.fr.unice.polytech.ghmandroid.NF;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HolderMatchingUser {
    private static final HolderMatchingUser ourInstance = new HolderMatchingUser();
    private List<User> usersOfRole;

    public static HolderMatchingUser getInstance() {
        return ourInstance;
    }

    private HolderMatchingUser() {
        this.usersOfRole=new ArrayList<>();
    }

    public List<User> getUsersOfRole() {
        return usersOfRole;
    }

    public void setUsersOfRole(List<User> usersOfRole) {
        this.usersOfRole = usersOfRole;
    }

    public ArrayList<String> getMatchingNames(){
        ArrayList<String> matchingNames = new ArrayList<>();
        for(User u : this.usersOfRole){
            matchingNames.add(u.getNom());
        }
        if(matchingNames.isEmpty()){
            Log.d("SINGLETON USER ROLE ", "MATCHING EMPTY");
        }
        return matchingNames;
    }
}
