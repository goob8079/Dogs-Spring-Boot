package com.justice.dogs.account.user;

import jakarta.persistence.Column;


public class User {

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
        super();
        this.enabled = false;
    }
}
