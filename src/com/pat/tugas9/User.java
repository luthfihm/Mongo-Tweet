package com.pat.tugas9;

import org.bson.Document;

/**
 * Created by luthfi on 11/12/2015.
 */
public class User {
    private String username;
    private String password;

    public User() {
        username = null;
        password = null;
    }

    public User(String username, String password) {
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

    public Document toBSON() {
        return new Document("username",username)
                .append("password",password);
    }
}
