package com.justice.dogs.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private String roles;

    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email;
    }
}
