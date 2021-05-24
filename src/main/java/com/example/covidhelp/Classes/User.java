package com.example.covidhelp.Classes;

public class User {
    public String username , email , usertype ;
    public boolean valid = true;

    public User(){

    }

    public User(String usertype, String username, String email, boolean valid){
        this.usertype = usertype;
        this.username = username;
         this.email = email;
         this.valid = valid;
    }
}
