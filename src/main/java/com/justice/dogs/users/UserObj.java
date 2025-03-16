package com.justice.dogs.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserObj {

    private @Id @GeneratedValue long id;
    private String username, email, password;

    public UserObj() {}

    public UserObj(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email;
    }
}
