package com.justice.dogs.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserObj {

    private @Id @GeneratedValue long id;
    private String username, email, password;

    public UserObj() {}
}
