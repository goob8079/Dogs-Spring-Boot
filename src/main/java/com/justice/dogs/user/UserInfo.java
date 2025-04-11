package com.justice.dogs.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// a class to store all a user's information
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    private String name, email, password, roles;
}
