package com.example.demo.database;

/**
 * Created by sondo on 17/03/2018.
 */
public class UsernamePassword {
    String username, password;


    public UsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
