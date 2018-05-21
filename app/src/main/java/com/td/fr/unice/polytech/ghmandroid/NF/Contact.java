package com.td.fr.unice.polytech.ghmandroid.NF;

import java.util.ArrayList;

public class Contact {
    private String name;
    private String phoneNo;

    public Contact(String name, String phoneNo){
        this.name=name.toLowerCase();
        this.phoneNo=phoneNo;
    }

    public boolean matchWithNames(ArrayList<String> names){
        for(String n : names){
            if(this.name.contains(n.toLowerCase()) || n.toLowerCase().contains(this.name)){
                return true;
            }
        }
        return false;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }
}
